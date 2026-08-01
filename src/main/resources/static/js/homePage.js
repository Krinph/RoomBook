admin();

function admin() {
	let isAdmin = sessionStorage.getItem("isAdmin")
	if (isAdmin) {
		let adminButton = document.getElementById('showAdmin');
		adminButton.style.display = 'inline-flex';
	}
}

function Reservation() {
	window.location.href = "/reservation";
}

function checkHistory() {
	window.location.href = "/checkHistory";
}