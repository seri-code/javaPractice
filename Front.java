package frontend;
import java.util.Scanner;
import backend.services.Arithmetic;

public class Front {
    public Front() { //생성자
		this.controller(); //함수(method)
		}

	// 기능을 수행하는 것이 아니라 프로그램의 데이터 플로우를 제어
	private void controller() {
		Scanner scanner = new Scanner(System.in);
		String[] subTitle = {"첫 번째 숫자 입력 : ", "1. 더하기  2. 빼기   3. 곱하기  4. 나누기 : ", "두 번째 숫자 입력 : "};
		 // 배열로 묶어줌 (숫자1 + 연산자 + 숫자2), length : 3(배열이 0,1,2)
		String[] userData = new String[3]; // 입력한 숫자값 저장
		String temp = null; //초기화
		boolean loop; // loop는 변수
		String title = this.getTitle(); // 정적인 그림은 고정시킴(메모리 절약)
		String menu = this.getMenu(); //getMenu에 있는 내용을 menu에 대입하겠다

		 while(true){ //true = while문 반복 , false = 빠져나감
			// Program Title 출력
			this.display(title); // 함수(매개변수 or parameter)
			this.display(menu);
			if(this.userInput(scanner).equals("0")) {break;} //break면 while문 전체를 빠져나감(실행되는게 없다)
			
			this.display(title); //그림

			for(int index=0; index < subTitle.length; index++) { //3 (0,1,2 아님)
				loop = true; // true를 loop에 대입
				this.display("\n"+subTitle[index]); //0 -> 1 -> 2
				// index 0, 1, 2 --> 숫자변환가능
				while(loop) { //while값이 true면 무한반복
					temp = this.userInput(scanner); //userInput 데이터를 temp에 대입
					loop = !this.isNumber(temp); //바로 위 temp //false인 경우 while문 탈출 //value에는 temp값이 대입된다
					if(index == 1) {
						if(this.isRange(1, 4, temp) == false) {
							loop = true;
						}
					}
				}
				userData[index] = temp; //user가 입력한 값이 숫자 변환이 가능하면 입력한 그 값을 저장해주겠다
				this.display(" ___________________________________________________________________\n");
			}

			// 연산
			Arithmetic operation = new Arithmetic(); //op:class의 인스턴스변수
			if(userData[1].equals("1")) {
				this.display(userData[0]+" + "+userData[2]+" = ");
				this.display(operation.plus(Integer.parseInt(userData[0]), Integer.parseInt(userData[2]))+"");
			}else if(userData[1].equals("2")){
				this.display(userData[0]+" - "+userData[2]+" = ");
				this.display(operation.minus(Integer.parseInt(userData[0]), Integer.parseInt(userData[2]))+"");	
			}else if(userData[1].equals("3")){
				this.display(userData[0]+" X "+userData[2]+" = ");
				this.display(operation.product(Integer.parseInt(userData[0]), Integer.parseInt(userData[2]))+"");	
			}else {
				this.display(userData[0]+" / "+userData[2]+" = ");
				this.display(operation.division(Integer.parseInt(userData[0]), Integer.parseInt(userData[2]))+"");
			}

			while(true) {
			System.out.print("\n계속하실거면 a를 입력해주세요 : ");
			String a = null; 
			a = userInput(scanner);
			if(a.equals("a")) {
				break;
			}
		  }
		}
		//scanner.close();
	}

 
	// 프로그램 타이틀 생성
	private String getTitle() {
		StringBuffer title = new StringBuffer(); //heap영역에 저장되는 메모리 절약
		title.append("\n\n\n\n"); //줄바꿈
		title.append("ᕦ(ò_óˇ)ᕤ___________________________________________________________\n");
		title.append("\n"); 
        title.append("                              HoonZzang Calc");
        title.append("\n");
        title.append("                                                Designed by Hz\n");
        title.append(" ____________________________________________________________________\n");
        
		return title.toString(); //리턴 하는 이유 : 
	}

	private String getMenu() {
		StringBuffer subMenu = new StringBuffer();
		subMenu.append("\n");
		subMenu.append("  1. 연산시작                      0. 종료\n");
		subMenu.append(" ______________________________________________ Select : ");

		return subMenu.toString();
	}

	// 숫자의 지정 범위 여부 판단 메서드
	private boolean isRange(int r1, int r2, String value) {  // tmep ==value== 10 
		boolean range = false;
		int v = Integer.parseInt(value);//temp = 유저가 입력한 값
		if(v >= r1 && v <= r2)	{ range = true;}
		return  range;
	}

	// 숫자변환가능여부 판단 메서드
	private boolean isNumber(String value) { //파라미터 이름은 상관없음
		boolean result = false; //boolean type인 result에 false를 대입하겠다      f    5
		try {
			Integer.parseInt(value);//입력한 값을 숫자로 변환시켜줌
			result = true;
		}catch(Exception e) {	}//try내용이 에러가 뜨면 catch문 실행
		return result;
	}

	// 입력기능
	private String userInput(Scanner sc) {
		return sc.next(); 
	}
 
	// 출력기능
	private void display(String text) { //함수(매개변수)와 함수 내의 실행되는 내용의 매개변수 이름은 같아야 함. text -> text
		System.out.print(text); //매개변수 이름은 맘대로(같은 함수만 가리키고 있으면 됌)
	}
}