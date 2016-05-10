function showButtons(id) {
	document.getElementById("updateButton" + id).style.display = "block";
	document.getElementById("removeButton" + id).style.display = "block";
	document.getElementById("editButton" + id).style.display = "none";
	document.getElementById("text" + id).disabled = false;
}
function deleteAuthorTags(id){
    if (confirm("Delete?") == true) {
    	/*document.forms["editForm"+id].setAttribute("edit", "remove");*/
    	var hiddenField = document.createElement("input");
    	hiddenField.setAttribute("type", "hidden")
    	hiddenField.setAttribute("name", "edit");
    	hiddenField.setAttribute("value", "remove");
    	document.forms["editForm"+id].appendChild(hiddenField);
    	document.forms["editForm"+id].submit();
    }
}