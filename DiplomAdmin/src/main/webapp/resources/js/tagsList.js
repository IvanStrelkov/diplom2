var SITE_URL = 	"http://localhost:8080/DiplomAdmin";

$(document).ready(function(){
	$.ajax({
		type : 'GET',
		url : SITE_URL + "/tag",
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		async : true,
		success : function(tagsList) {
			$.each(tagsList, function(ind, tag) {
				drawTag(tag);
			})
			drawSaveTag();
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert(jqXHR.status + ' ' + jqXHR.responseText);
		}
	});
});

function drawSaveTag() {
	var addTag = $('#addTagDiv');
	addTag.append('<table class="save"><tr><td>' + $('#addTag').val() + ': </td><td>' + 
			'<input id=inputTag0 type="text" value="" size="30"></td>'+
			'<td><button class="btn btn-primary btn-space" onclick="saveTag()">' + $('#buttonSave').val() + '</button></td></tr></table>');
}

function drawTag(tag) {
	var tags = $('#listTagsDiv');
	var part1 = '<table id="tableTag' + tag.id + '" class="edit"><tr><td>' + $('#tagLabel').val() + ': </td><td>' + '<input id=inputTag' + tag.id + ' type="text" value="' + 
		tag.name +'" size="30" disabled></td>';
	var part2 = '<td><button class="btn btn-primary btn-space" id="buttonEdit' + tag.id + '" onclick="editTag(' + tag.id + ')">' + $('#buttonEdit').val() + '</button></td>'+
		'<td><button class="btn btn-primary btn-space" id="buttonUpdate' + tag.id + '" hidden="true" onclick="updateTag(' + tag.id + ')">' + $('#buttonUpdate').val() + '</button></td>' +
		'<td><button class="btn btn-primary btn-space" id="buttonRemove' + tag.id + '" hidden="true" onclick="removeTag(' + tag.id + ')">' + $('#buttonRemove').val() + '</button></td></tr></table>';
	tags.append(part1 + part2);
	$('#buttonUpdate' + tag.id).hide();
	$('#buttonRemove' + tag.id).hide();
}

function editTag(tagId) {
	$('#inputTag' + tagId).prop('disabled', false);
	$('#buttonEdit' + tagId).toggle(500);
	$('#buttonUpdate' + tagId).toggle(500);
	$('#buttonRemove' + tagId).toggle(500);
}

function removeTag(tagId) {
	$.ajax({
		type : 'DELETE',
		url : SITE_URL + "/tag/" + tagId,
		async : true,
		success : function() {
			$('#tableTag' + tagId).remove();
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert(jqXHR.status + ' ' + jqXHR.responseText);
		}
	});
}

function saveTag() {
	var tagName = $('#inputTag0').val();
	var JSONObject = {
			'id' : 0,
			'name' : tagName
	};
	$.ajax({
		type : 'POST',
		url : SITE_URL + "/tag",
		contentType : 'application/json; charset=utf-8',
		data: JSON.stringify(JSONObject),
		dataType : 'json',
		async : true,
		success : function(tag) {
			drawTag(tag);
			$('#inputTag0').val("")
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert(jqXHR.status + ' ' + jqXHR.responseText);
		}
	});
}

function updateTag(tagId) {
	var tagName = $('#inputTag' +tagId).val();
	var JSONObject = {
			'id' : tagId,
			'name' : tagName
	};
	$.ajax({
		type : 'PUT',
		url : SITE_URL + "/tag",
		contentType : 'application/json; charset=utf-8',
		data: JSON.stringify(JSONObject),
		dataType : 'json',
		async : true,
		success : function(tag) {
			$('#inputTag' + tagId).prop('disabled', true);
			$('#buttonEdit' + tagId).toggle(500);
			$('#buttonUpdate' + tagId).toggle(500);
			$('#buttonRemove' + tagId).toggle(500);		
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert(jqXHR.status + ' ' + jqXHR.responseText);
		}
	});
}