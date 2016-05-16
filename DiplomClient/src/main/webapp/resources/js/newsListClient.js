var SITE_URL = 	"http://localhost:8080/DiplomClient";
var search_start = 0;
var search_count = 12;
var authorId = 0;
var tagsId = "";
$(document).ready(function(){
	var newsList = getNews();
	if(newsList)
		drawNewsList(newsList);

	$.ajax({
		type : 'GET',
		url : SITE_URL + "/tag",
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		async : true,
		success : function(tagsList) {
			var tags = $('#mutliSelectTags');
			var insideTags = "";
			$.each(tagsList, function(ind, tag) {
				tags.append($("<option></option>")
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

	$.ajax({
		type : 'GET',
		url : SITE_URL + "/author",
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		async : true,
		success : function(authorList) {
			var authors = $('#selectAuthor');
			$.each(authorList, function(ind, author) {
				authors.append($('<option class="authorOption"></option>')
						.attr("value",author.id)
						.text(author.name));
			})
			$('#selectAuthor').show();
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert(jqXHR.status + ' ' + jqXHR.responseText);
		}
	});
});

function getNews(){
	$.ajax({
		type : 'GET',
		url : SITE_URL + '/news/search?start=' + search_start + '&count=' + search_count + '&tagsId=' + tagsId + '&authorId=' + authorId,
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		async : false,
		success : function(newsList) {
			if (newsList) {
				drawNewsList(newsList)
			} else {

			}
		}
	});
}

function drawNewsList(newsList){
	if (search_start == 0) {
		$("#newsDiv").empty();
	}
	$.each(newsList, function(ind, news) {
		if (!news) {
			return;
		}
		search_start++;
		var tags = "";
		$.each(news.tags, function(ind, tag) {
			if (tags != "") {
				tags += ", "
			}
			tags += tag.name;
		})

		var date = news.modificationDate;

		if(!date) {
			date = news.creationDate;
		}
		var dateFormat = $("#myDateFormat").val();
		var formated_date = moment(date).format(dateFormat);	

		$("#newsDiv").append(
				'<table class="news">' + 
				'<tr><td colspan="2" class="listTitle"><a href="' + SITE_URL + '/view/' + news.id + '">' + news.title + '</a></td><tr>' +
				'<tr><td colspan="2" class="listShort">' + news.shortText + '</td><tr>' +
				'<tr><td class="listAuthor">' + news.author.name + '</td>' + '<td class="listTags">' + tags + '</td></tr>' +
				'<tr><td class="listComments"> Comments(' + news.comments.length + ')</td><td class="listDate">' + formated_date + '</td></tr></table>').fadeIn();
	})
}

function search() {
	search_start = 0;
	tagsId = $('#mutliSelectTags').val();
	authorId = $("#selectAuthor").find(":selected").val();
	if(!tagsId)
		tagsId = "";
	if(!authorId)
		authorId = 0;
	getNews();
}

function reset() {
	search_start = 0;
	tagsId = "";
	authorId = 0;
	var a = $("#mutliSelectTags");
	$("#selectAuthor").val("0");
	$("#mutliSelectTags > option").attr("selected",false);
	$("#mutliSelectTags").multiselect('refresh');
	getNews();
}

$(window).scroll(function() {
	var hT = $('#endListNews').offset().top,
	hH = $('#endListNews').outerHeight(),
	wH = $(window).height(),
	wS = $(window).scrollTop();
	if (wS > (hT+hH-wH)){
		getNews();
	}
});
