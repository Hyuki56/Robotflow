async function populate() {
  const response = await fetch('/hyuk/json/hyuk_test.json');
  const data = await response.json();
  populateHeader(data);

  await updateAllClassroomStatus(data); // ✅ 통합된 함수로 교체

  setInterval(() => {
    updateAllClassroomStatus(data);
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

async function updateAllClassroomStatus(obj) {
  const section = document.querySelector('section');
  section.innerHTML = ''; // ❗️한 번만 지우고

  // 두 강의실 데이터 한 번에 가져옴
  const [seatData1, seatData2] = await Promise.all([
    fetchSeatStatus(1),
    fetchSeatStatus(2),
  ]);

  const allSeatData = {
    class1: seatData1,
    class2: seatData2
  };

  for (const room of obj.members) {
    const roomName = room.name; // class1, class2
    const seats = allSeatData[roomName][roomName];

    if (!seats) {
      console.error(`Room ${roomName} has no seat data`);
      continue;
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
