async function populate() {
  const response = await fetch('/hyuk/json/hyuk_test.json'); // ✅ 절대 경로
  const data = await response.json();
  populateHeader(data);

  // 두 개의 classNum을 비동기적으로 처리
  await Promise.all([
    updateClassroomStatus(data, 1), // Class 1
    updateClassroomStatus(data, 2)  // Class 2
  ]);

  setInterval(() => {
    // 5초마다 두 개의 classNum을 동시에 갱신
    Promise.all([
      updateClassroomStatus(data, 1),
      updateClassroomStatus(data, 2)
    ]);
  }, 5000);
}

function populateHeader(obj) {
  const header = document.querySelector('header');
  const myH1 = document.createElement('h1');
  myH1.textContent = obj.Name;
  header.appendChild(myH1);

  const myPara = document.createElement('p');
  myPara.textContent = `개발 기간 ${obj.startDate} ~ ${obj.endDate}`;
  header.appendChild(myPara);
}

async function fetchSeatStatus(classNum) {
  const res = await fetch(`/api/class${classNum}/seats`);
  return await res.json();
}

async function updateClassroomStatus(obj, classNum) {
  const seatData = await fetchSeatStatus(classNum); // classNum을 전달
  const section = document.querySelector('section');
  section.innerHTML = '';

  for (const room of obj.members) {
    const roomName = room.name;
    const seats = seatData[roomName];

    if (!seats) { // seats가 undefined 또는 null일 경우 처리
      console.error(`Room ${roomName} has no seat data`);
      continue; // 다음 room으로 넘어가기
    }

    const total = Object.keys(seats).length;
    const used = Object.values(seats).filter(status => status === 'used').length;
    const available = total - used;

    const article = document.createElement('article');
    const title = document.createElement('h2');
    const prof = document.createElement('p');
    const age = document.createElement('p');
    const info = document.createElement('p');
    const ul = document.createElement('ul');

    const link = document.createElement('a');
    const roomNum = roomName.match(/\d+/)?.[0];
    link.href = `classRoom/class${roomNum}.html`;
    title.textContent = roomName;
    link.appendChild(title);

    prof.textContent = `교수명: ${room.professor}`;
    age.textContent = `연령: ${room.age}`;
    info.textContent = '강의실 현황:';

    const li1 = document.createElement('li');
    li1.textContent = `총 자리: ${total}`;
    const li2 = document.createElement('li');
    li2.textContent = `사용 중인 자리: ${used}`;
    const li3 = document.createElement('li');
    li3.textContent = `남은 자리: ${available}`;

    ul.appendChild(li1);
    ul.appendChild(li2);
    ul.appendChild(li3);

    article.appendChild(link);
    article.appendChild(prof);
    article.appendChild(age);
    article.appendChild(info);
    article.appendChild(ul);
    section.appendChild(article);
  }
}

// 한 번에 1, 2를 불러오기
populate();
