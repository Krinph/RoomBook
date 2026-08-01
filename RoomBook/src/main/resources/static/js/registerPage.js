function Register() {
	let telephone = document.getElementById('Telephone').value;
	let name = document.getElementById('Name').value;
	let password = document.getElementById('Password').value;
	let confirmPassword = document.getElementById('ConfirmPassword').value;
	console.log(telephone, password);
	if (password !== confirmPassword) { alert('Passwords do not match!'); }

	const res = fetch("/regis", {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify({'telephone': telephone,'name': name, 'password': password})
	})
	.then(res => res.json())
	.then(data => {
		console.log(data);
		if (data.success) {
			sessionStorage.setItem('isAdmin', data.isAdmin);
			sessionStorage.setItem('ID', data.id);
			alert("注册成功！请返回登录！")
			window.location.href = "/login";
		}
		if (data.message) {
			alert(data.message);
		}
	})

}