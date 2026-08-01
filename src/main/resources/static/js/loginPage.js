function login() {
	let telephone = document.getElementById("Telephone").value;
	let password = document.getElementById("password").value;
	fetch ("/match", {
		method: "POST",
		headers: {
			"Content-Type": "application/json"
		},
		body: JSON.stringify({"telephone": telephone, "password": password})
	})
		.then(response => { return response.json(); })
		.then(data => {
			if (data.success) {
				sessionStorage.setItem('isAdmin', data.isAdmin);
				sessionStorage.setItem('ID', data.id);
				window.location.href = "/home";
			} else {
				alert("登录失败！");
			}
		})
}