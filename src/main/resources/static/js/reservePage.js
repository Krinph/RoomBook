initialize();

function initialize() {
	let roomList = [];
	fetch('/initialize', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		}
	})
		.then(res => res.json())
		.then(data => {
			if (data && data.success) {
				roomList = data.roomList;
			} else if (data.message) {
				alert(data.message);
			} else {
				alert('error');
			}
		})
	showList(roomList);
}

function searchRoom() {
	let roomInfo = document.getElementById('inputSearch');
	let roomList = document.getElementById('roomList');
	fetch('/searchRoom', {
		method: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		body: JSON.stringify({'info': roomInfo})
	})
		.then(res => res.json())
		.then(data => {
			if (data && data.success) {
				roomList = data.roomList;
			} else if (data.message) {
				alert(data.message);
			} else { alert('error'); }
		})
	showList(roomList);
}

function showList(roomList) {
	let List = document.getElementById("roomLists");
	List.innerHTML = "";
	for (let line of roomList) {
		let html = "";
		html += "<li>";
		html += "<strong>" + line.location + "</strong>";
		html += "<span>容量：<span className='capacity'>" + line.size + "</span></span>";
		html += "<span>空闲时段：" + line.startTime + "-" + line.endTime + "</span>";
		html += "</li>";
		List.innerHTML += html;
	}

}