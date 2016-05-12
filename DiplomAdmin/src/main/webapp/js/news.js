var search_start = 0;
var search_count = 10;
var userPrefix = "DiplomAdmin/users"

$(document).ready(function(){
	var tagsId = $('.tags:checked').val();
	var authorId = $("#author").val
	$.ajax({
		type : 'GET',
		url : userPrefix + '/search?start=' + search_start + '&count=' + search_count + '&tagsId' + tagsId + '&authorId=authorId',
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		async : true,
		success : function(news) {
			
		},
		error : function(jqXHR, textStatus, errorThrown) {
			alert(jqXHR.status + ' ' + jqXHR.responseText);
		}
	});
});