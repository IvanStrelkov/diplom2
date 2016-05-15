var SITE_URL = 	"http://localhost:8080/DiplomAdmin/admin";

$(document).ready(function(){
	$.ajax({
		type : 'GET',
		url : SITE_URL + "/author",
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		async : true,
		success : function(authorsList) {
			$.each(authorsList, function(ind, author) {
				drawAuthor(author);
			})
			drawSaveAuthor();
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert(jqXHR.status + ' ' + jqXHR.responseText);
		}
	});
});

function drawSaveAuthor() {
	var addAuthor = $('#addAuthorDiv');
	addAuthor.append('<table class="save"><tr><td>' + $('#addAuthor').val() + ': </td><td>' + 
			'<input id=inputAuthor0 type="text" value="" size="30"></td>'+
			'<td><button class= "btn btn-primary btn-space" onclick="saveAuthor()">' + $('#buttonSave').val() + '</button></td></tr></table>');
}

function drawAuthor(author) {
	var authors = $('#listAuthorsDiv');
	var part1 = '<table id="tableAuthor' + author.id + '" class="edit"><tr><td>' + $('#authorLabel').val() + ': </td><td>' + '<input id=inputAuthor' + author.id + ' type="text" value="' + 
		author.name +'" size="30" disabled></td>';
	var part2 = '<td><button class="btn btn-primary btn-space" id="buttonEdit' + author.id + '" onclick="editAuthor(' + author.id + ')">' + $('#buttonEdit').val() + '</button></td>'+
		'<td> <button class="btn btn-primary btn-space" id="buttonUpdate' + author.id + '" hidden="true" onclick="updateAuthor(' + author.id + ')">' + $('#buttonUpdate').val() + '</button></td>' +
		'<td> <button class="btn btn-primary btn-space" id="buttonRemove' + author.id + '" hidden="true" onclick="removeAuthor(' + author.id + ')">' + $('#buttonRemove').val() + '</button></td></tr></table>';
	authors.append(part1 + part2);
	$('#buttonUpdate' + author.id).hide();
	$('#buttonRemove' + author.id).hide();
}

function editAuthor(authorId) {
	$('#inputAuthor' + authorId).prop('disabled', false);
	$('#buttonEdit' + authorId).toggle(500);
	$('#buttonUpdate' + authorId).toggle(500);
	$('#buttonRemove' + authorId).toggle(500);
}

function removeAuthor(authorId) {
	$.ajax({
		type : 'DELETE',
		url : SITE_URL + "/author/" + authorId,
		async : true,
		success : function() {
			$('#tableAuthor' + authorId).remove();
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert(jqXHR.status + ' ' + jqXHR.responseText);
		}
	});
}

function saveAuthor() {
	var authorName = $('#inputAuthor0').val();
	var JSONObject = {
			'id' : 0,
			'name' : authorName
	};
	$.ajax({
		type : 'POST',
		url : SITE_URL + "/author",
		contentType : 'application/json; charset=utf-8',
		data: JSON.stringify(JSONObject),
		dataType : 'json',
		async : true,
		success : function(author) {
			drawAuthor(author);
			$('#inputAuthor0').val("")
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert(jqXHR.status + ' ' + jqXHR.responseText);
		}
	});
}

function updateAuthor(authorId) {
	var authorName = $('#inputAuthor' +authorId).val();
	var JSONObject = {
			'id' : authorId,
			'name' : authorName
	};
	$.ajax({
		type : 'PUT',
		url : SITE_URL + "/author",
		contentType : 'application/json; charset=utf-8',
		data: JSON.stringify(JSONObject),
		dataType : 'json',
		async : true,
		success : function(author) {
			$('#inputAuthor' + authorId).prop('disabled', true);
			$('#buttonEdit' + authorId).toggle(500);
			$('#buttonUpdate' + authorId).toggle(500);
			$('#buttonRemove' + authorId).toggle(500);		
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert(jqXHR.status + ' ' + jqXHR.responseText);
		}
	});
}