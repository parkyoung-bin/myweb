package com.myweb.util;

public class PageVO {
	//화면에 그려질 pageNation을 계산하는 클래스 ( 생성할 때 pageNum와 전체 게시글 수를 가지고 계싼)
	
	private int startPage; //게시글 화면에 보여질 첫페이지 번호
	private int endPage; // 페이지 마지막 번호
	private boolean prev,next; //다음,이전버튼 활성화 여부
	
	private int pageNum; //현재 조회하는 페이지 번호
	private int total; //전체 게시글 수
	
	private int amount = 20; // 한 페이지에서 몇개의 데이터를 보여줄건가
	
	//생성될 때 계산처리
	public PageVO(int pageNum,int total,int amount) {
		
		this.pageNum = pageNum;
		this.total = total;
		this.amount = amount;
		//1.end페이지 먼저 계산
		//현재 페이지가 1~10번이면 > 화면에 보여질 끝 페이지 10
		//14번이라면 > 화면에 보여질 끝 페이지는 20
		//공식 : (int)Math.ceil(페이지 번호 /(double)보여질 페이지 수)*보여질 페이지 수
		this.endPage=(int)Math.ceil(this.pageNum/(double)10) *10;
		//2.start페이지 계산
		//공식: 끝페이지 - 보여질 페이지 수 +1
		this.startPage = endPage - 10 + 1;
		//3.실제 보여질 페이지 계산
		//만약 총 게시물이 52개 라면 ? -> 페이지 번호는 6까지 표시 돼야 함
		//만약 총 게시물이 105개라면? -> 페이지번호는 11까지 표시 돼야함
		//공식 : 실제 끝 페이지번호= (int)Math.ceil(전체게시글 수 /페이지에 보여지는 데이터 수)
		
		int realEnd = (int)Math.ceil(this.total/(double)this.amount);
		
		//ex:131개의 게시물
		//1번 페이지 클릭시 -> endPage는 10, realEnd은 14(endPage로 세팅)
		//11번 페이지 클릭시 -> endPage는 20, realEnd은 14(realEnd로 세팅)
		//결론 : endPage 번호 > realEnd 번호 라면 realEnd를 보여줌
		
		if(this.endPage > realEnd) {
			this.endPage = realEnd;
		}
		
		//4.이전버튼
		//startPage 는 1, 11, 21 ... 101 형태로 표기
		//startPage가 1보다 큰 경우는 true
		this.prev = this.startPage>1;
		
		
		//5.다음버튼
		this.next = realEnd > this.endPage;
		
		
	}
		//게터, 세터

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
		
}
	

