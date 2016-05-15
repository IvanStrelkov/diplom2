var SITE_URL = 	"http://localhost:8080/DiplomAdmin";
var currentNews;

$(document).ready(function(){
	var news_id = $("#news_id").val();
	if(news_id) {
		$.ajax({
			type : 'GET',
			url : SITE_URL + "/news/" + news_id,
			contentType : 'application/json; charset=utf-8',
			dataType : 'json',
			async : true,
			success : function(news) {
				currentNews = news;
				drawViewingNews(news);
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert(jqXHR.status + ' ' + jqXHR.responseText);
			}
		});
	} else {
		drawAddingNews();
	}
});

function drawViewingNews(news) {
	var news_id = $("#news_id").val();

	var dateMessage1 = '';
	var dateMessage2 = '';
	var dateFormat = $("#myDateFormat").val();

	var tags = "";
	$.each(currentNews.tags, function(ind, tag) {
		if (tags != "") {
			tags += ", "
		}
		tags += tag.name;
	})

	var date = currentNews.modificationDate;
	if(date) {
		dateMessage1 += $("#modificationDate").val() + ' - ' + moment(date).format(dateFormat);
	}
	date = currentNews.creationDate;
	dateMessage2 += $("#creationDate").val() + ' - '+ moment(date).format(dateFormat);

	$("#divNews").append(
			'<table class="news">' + 
			'<tr><td colspan="2" class="listTitle"><p class="newsTitle">' + currentNews.title + '</p></td><tr>' +
			'<tr><td colspan="2" class="listShort"><p class="newsShort">' + currentNews.shortText + '</p></td><tr>' +
			'<tr><td colspan="2" class="listFull"><p class="newsFull">' + currentNews.fullText + '</p></td><tr>' +
			'<tr><td><p class="newsAuthor">' + currentNews.author.name + '</p></td>' + '<td class="listTags">' + 
			'<p class="newsTags">' + tags + '</p></td></tr>' +
			'<tr><td><p class="newsDate">' + dateMessage2 + '</p></td><td><p class="newsDate">' + dateMessage1 + '</p></td>' +
			'<tr><td></td><td class="buttons"><button class="btn btn-primary btn-space" onclick="editNews()">' + $("#buttonEdit").val() + '</button>' + 
			'<button class="btn btn-primary btn-space" onclick="deleteNews()">' + $('#buttonRemove').val() + '</button></td></tr>'
	).fadeIn();
	$.each(news.comments, function(ind, comment) {
		drawComment(comment);
	})
	$('#divAddComment').append(
			'<table class="addComment"><tr><td colspan="3"><textarea id="commentText"></textarea></td></tr>' + 
			'<tr><td colspan="2"></td><td class="commentButton"><button class="btn btn-primary btn-space" onclick="addComment()">' + $('#addCommentButton').val() + '</button></td></tr></table>'
	)
}

function editNews() {
	$("#divNews").empty();
	$("#divAddComment").empty();
	$("#divComments").empty();
	drawEditingNews();
}

function deleteNews() {
	$.ajax({
		type : 'DELETE',
		url : SITE_URL + "/news/" + currentNews.id,
		async : true,
		success : function() {
			window.location.href = SITE_URL + '/portal';
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert(jqXHR.status + ' ' + jqXHR.responseText);
		}
	});
}

function drawEditingNews() {
	$('#newsTitle').empty().append(
			'<input type="text" class="title" id="inputNewsTitle" value="' + currentNews.title + '"/>'
	);

	var date = new Date();
	var dateFormat = $("#myDateFormat").val();
	var formated_date = moment(date).format(dateFormat);
	$('#newsDate').empty().append(formated_date);

	$('#newsShortText').empty().append(
			'<textarea id="textNewsShort" rows="5">' + currentNews.shortText + '</textarea>'
	);

	$('#newsFullText').empty().append(
			'<textarea id="textNewsFull" rows="10">' + currentNews.fullText + '</textarea>'
	);

	var tags = "";
	$.each(currentNews.tags, function(ind, tag) {
		if (tags != "") {
			tags += ","
		}
		tags += tag.id;
	})

	drawAllTags();
	drawAllAuthor();
	first = false;

	$("#selectAuthor").val(currentNews.author.id);

	tags.split(',').forEach(function (tagId) {
		$("#selectTag" + tagId).attr("selected", true);
	}); 
	$("#mutliSelectTags").multiselect('refresh');

	$('#newsButtons').empty().append(
			'<button  class="btn btn-primary btn-space" onclick="saveNews()">' + $('#buttonUpdate').val() + '</button>' + 
			'<button  class="btn btn-primary btn-space" onclick="cancelNews()">' + $('#buttonCancel').val() + '</button>'
	);
	$("#editTable").show();
}

function cancelNews() {
	window.location.href = SITE_URL + '/view/' + currentNews.id;
}

function drawAddingNews() {
	$('#newsTitle').append(
			'<input type="text" class="title" id="inputNewsTitle"/>'
	);

	var date = new Date();
	var dateFormat = $("#myDateFormat").val();
	var formated_date = moment(date).format(dateFormat);
	$('#newsDate').append(formated_date);

	$('#newsShortText').append(
			'<textarea id="textNewsShort" rows="5"/>'
	);

	$('#newsFullText').append(
			'<textarea id="textNewsFull" rows="10"/>'
	);

	drawAllTags();

	drawAllAuthor();

	$('#newsButtons').append(
			'<button  class="btn btn-primary btn-space" onclick="saveNews()">' + $('#buttonSave').val() + '</button>'
	);
	$("#editTable").show();
}

function drawComments(news) {

}

function drawAllTags() {
	$.ajax({
		type : 'GET',
		url : SITE_URL + "/tag",
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		async : false,
		success : function(tagsList) {
			var tags = $('#mutliSelectTags');
			var insideTags = "";
			$.each(tagsList, function(ind, tag) {
				tags.append($("<option></option>")
						.attr("id", 'selectTag' + tag.id)
						.attr("value",tag.id)
						.text(tag.name));
			})
			$('#mutliSelectTags').multiselect({
				includeSelectAllOption: true,	
				nonSelectedText:$('#tagsText').val()
			});
			$('#mutliSelectTags').show();
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert(jqXHR.status + ' ' + jqXHR.responseText);
		}
	});
}

function drawAllAuthor() {
	$.ajax({
		type : 'GET',
		url : SITE_URL + "/author",
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		async : false,
		success : function(authorList) {
			var authors = $('#selectAuthor');
			$.each(authorList, function(ind, author) {
				authors.append($("<option></option>")
						.attr("value", author.id)
						.text(author.name));
			})
			$('#selectAuthor').show();
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert(jqXHR.status + ' ' + jqXHR.responseText);
		}
	});
}

function saveNews() {
	var news  = {};
	news.title = $('#inputNewsTitle').val();
	news.shortText = $('#textNewsShort').val();
	news.fullText = $('#textNewsFull').val();
	news.author = {};
	tagsId = $('#mutliSelectTags').val() + '';
	news.author.id = $("#selectAuthor").find(":selected").val();
	news.tags = []
	tagsId.split(",").forEach(function (tagId) {
		var tag = {};
		tag.id = tagId;
		tag.name = "";
		news.tags.push(tag);
	});
	if ($("#news_id").val()) {
		news.id = $("#news_id").val()
		news.modificationDate = new Date();
		news.creationDate = currentNews.creationDate;
		$.ajax({
			type : 'PUT',
			url : SITE_URL + "/news",
			contentType : 'application/json; charset=utf-8',
			data: JSON.stringify(news),
			dataType : 'json',
			async : true,
			success : function(news) {
				window.location.href = SITE_URL + '/view/' + currentNews.id;
			}
		});
	} else {
		news.creationDate = new Date();
		$.ajax({
			type : 'POST',
			url : SITE_URL + "/news",
			contentType : 'application/json; charset=utf-8',
			data: JSON.stringify(news),
			dataType : 'json',
			async : true,
			success : function(news) {
				window.location.href = SITE_URL + '/view/' + news.id;
			}
		});
	}
}

function drawComment(comment) {
	var dateFormat = $("#myDateFormat").val();
	var date = moment(comment.creationDate).format(dateFormat)
	$("#divComments").append(
			'<table class="addComment" id = "commentTable' + comment.id + '"><tr><td class="commentText" colspan="3">' + comment.text + '</td></tr>' + 
			'<td class="commentDate">' + date + '</td>' + '<td></td>' + 
			'<td class="commentButton"><button class="btn btn-primary btn-xs" onclick="deleteComment(' + comment.id + ')">' +
			'<span class="glyphicon glyphicon-remove"></span></button></td></tr></table>'
	);
}

function deleteComment(commentId) {
	$.ajax({
		type : 'DELETE',
		url : SITE_URL + "/comment/" + commentId,
		async : true,
		success : function() {
			$("#commentTable" + commentId).remove();
		}
	});
}

function addComment() {
	var comment = {};
	comment.id = "0";
	comment.text = $('#commentText').val();
	comment.creationDate = new Date();
	comment.newsId = currentNews.id;
	$.ajax({
		type : 'POST',
		url : SITE_URL + "/comment",
		contentType : 'application/json; charset=utf-8',
		data: JSON.stringify(comment),
		dataType : 'json',
		async : true,
		success : function(savedComment) {
			$('#commentText').val("");
			drawComment(savedComment);
		}
	});
}