var SITE_URL = 	"http://localhost:8080/DiplomClient";
var currentNews;

$(document).ready(function(){
	var news_id = $("#news_id").val();
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
			'<tr><td><p class="newsDate">' + dateMessage2 + '</p></td><td><p class="newsDate">' + dateMessage1 + '</p></td></tr></table>'
	).fadeIn();
	$.each(news.comments, function(ind, comment) {
		drawComment(comment);
	})
	$('#divAddComment').append(
			'<table class="addComment"><tr><td colspan="3"><textarea rows="4" id="commentText"></textarea></td></tr>' + 
			'<tr><td colspan="2"></td><td class="commentButton"><button class="btn btn-primary btn-space" onclick="addComment()">' + $('#addCommentButton').val() + '</button></td></tr></table>'
	)
}

function drawComment(comment) {
	var dateFormat = $("#myDateFormat").val();
	var date = moment(comment.creationDate).format(dateFormat)
	$("#divComments").append(
			'<table class="addComment" id = "commentTable' + comment.id + '">' + 
			'<td class="commentDate">' + date + '</td>' + '<td></td>' + 
			'<td></td></tr>' + 
			'<tr><td class="commentText" colspan="3">' + comment.text + '</td></tr></table><br>'
	);
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