function bindEvents(){
    $('#register').on('click', registerClick);
}

function registerClick(){
	$('#register-modal').modal('show');
}

function updateClick(categoryName){
	$('#update-modal').modal('show');
	$('#update-name').val(categoryName);
}
