console.log("working fine ");

const toggleSidebar= () => {
	if ($(".sidebar").is(":visible")){
		$(".sidebar").css("display","none");
		$(".content").css("margin-left","0%");
	}	
	else{
		$(".sidebar").css("display","block");
		$(".content").css("margin-left","20%");

	}
	
};

const search = () => {
	//console.log("searching ");
	
  let query = $("#search-input").val();
 //console.log(query);
 
 if(query==""){
	$(".search-result").hide();	
	} else {
	console.log(query);
	//const q1=query;
		
	//semding request to the servere for data list
	let url = `http://localhost:8383/search/${query}`;
	
	fetch(url).then( response => response.json())
	.then((data) =>{
		//data
		//console.log(data);
		
		let text =`<div class="list-group">`;
		
		data.forEach((contact) => {
		    //const c1=contact;
			text += `<a href="/user/contact/${contact.cId}" class="list-group-item list-group-item-action"> ${contact.name} </a>`;	
		});
		
		text+=`</div>`;
		$(".search-result").html(text);
		$(".search-result").show();
	});
	
	
 }
};


document.getElementById('change-password-form').addEventListener('submit', function(event) {
  event.preventDefault();

  // Get values from the form
  const oldPassword = document.getElementById('old-password').value;
  const newPassword = document.getElementById('new-password').value;
  const confirmPassword = document.getElementById('confirm-password').value;
  const errorMessage = document.getElementById('error-message');

  // Reset error message
  errorMessage.textContent = '';

  // Validate passwords
  if (newPassword !== confirmPassword) {
    errorMessage.textContent = 'New passwords do not match.';
    return;
  }

  if (newPassword.length < 8) {
    errorMessage.textContent = 'Password must be at least 8   characters long.';
    return;
  }

  if (oldPassword === newPassword) {
    errorMessage.textContent = 'New password cannot be the same as the old password.';
    return;
  }
  
  // If all validations pass
  //alert('Password changed successfully!');
});














