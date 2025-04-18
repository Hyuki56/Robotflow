$(document).ready(function() {
	let isOverList = [];

	// 메뉴 번호를 설정 (1 ~ 5까지)
	for (let i = 1; i <= 5; i++) {
		isOverList[i] = { trigger: false, menu: false };

		const openSelector = `.openAll${i}`;
		const menuSelector = `.gnb_depth2_${i}`;

		const goHide = function(index) {
			if (!isOverList[index].trigger && !isOverList[index].menu) {
				$(`.gnb_depth2_${index}`).stop().fadeOut('fast');
			}
		};

		// open 영역 mouseover / focus
		$(openSelector).on('mouseover focus', function() {
			if (parseInt($('header').css('width')) > 800) {
				$(menuSelector).fadeIn('fast');
			}
			isOverList[i].trigger = true;
		});

		// open 영역 mouseout
		$(openSelector).mouseout(function() {
			isOverList[i].trigger = false;
			setTimeout(() => goHide(i), 200);
		});

		// menu 마지막 a태그 blur
		$(`${menuSelector} li:last-child a`).blur(function() {
			isOverList[i].trigger = false;
			setTimeout(() => goHide(i), 200);
		});

		// menu mouseover / focus
		$(menuSelector).on('mouseover focus', function() {
			isOverList[i].menu = true;
		});

		// menu mouseout / blur
		$(menuSelector).on('mouseout blur', function() {
			isOverList[i].menu = false;
			setTimeout(() => goHide(i), 200);
		});
	}

	// 스크롤에 따라 to_top 보이기
	if ($(document).scrollTop() < 50) $('.to_top').addClass('hide');
	else $('.to_top').removeClass('hide');

	$(window).scroll(function() {
		if ($(document).scrollTop() < 50) $('.to_top').addClass('hide');
		else $('.to_top').removeClass('hide');
	});

	// 모바일 메뉴
	$('.openMOgnb').click(function() {
		$('header').addClass('on');
		$('header .header_cont').slideDown('fast');
		$('header .header_area .header_cont .closePop').show();
		$("body").on('touchmove', function(e) { e.preventDefault(); });
	});
	$('header .header_cont .closePop').click(function() {
		$('.header_cont').slideUp('fast');
		$('header').removeClass('on');
		$("body").off('touchmove');
	});

	// 윈도우 리사이즈
	$(window).resize(function() {
		if (parseInt($('header').css('width')) > 800) $('.header_cont').show();
	});

	// 프로그램 더보기 버튼
	$('.program_list li .btn_more a').click(function() {
		const subtxt = $(this).closest('li').find('.subtxt');
		if (subtxt.is(':hidden')) {
			subtxt.css('display', 'inline');
			$(this).text('접기');
		} else {
			subtxt.css('display', 'none');
			$(this).text('더보기');
		}
	});
});
