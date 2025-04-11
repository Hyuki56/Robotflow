document.addEventListener('DOMContentLoaded', () => {
    console.log('페이지 로드 완료!');

    fetch('/kwj/json/data.json')
        .then(response => response.json())
        .then(data => {
            console.log('JSON 내용:', data);
            document.getElementById('message').textContent = data.message;
        })
        .catch(error => {
            console.error('JSON 로드 실패:', error);
        });
});
