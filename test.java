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
		
		String[] userData = new String[3]; 
		String temp = null; //초기화
		boolean loop; // loop는 변수
		String title = this.getTitle();
		String menu = this.getMenu(); 

		 while(true){ 
			// Program Title 출력
			this.display(title); // 함수(매개변수 or parameter)
			this.display(menu);
			if(this.userInput(scanner).equals("0")) {break;} 
			this.display(title); //그림

			for(int index=0; index < subTitle.length; index++) { //3 (0,1,2 아님)
				loop = true; // true를 loop에 대입
				this.display("\n"+subTitle[index]); //0 -> 1 -> 2
				// index 0, 1, 2 --> 숫자변환가능
				while(loop) { //while값이 true면 무한반복
					temp = this.userInput(scanner); 
					loop = !this.isNumber(temp); 
					if(index == 1) {
						if(this.isRange(1, 4, temp) == false) {
							loop = true;
						}
					}
				}
				userData[index] = temp; 
				this.display(" ___________________________________________________________________\n");
			}

			// 연산
			Arithmetic operation = new Arithmetic(); 
			if(userData[1].equals("1")) {
				this.display(userData[0]+" + "+userData[2]+" = ");
				this.display(operation.plus(Integer.parseInt(userData[0]), Integer.parseInt(userData[2]))+"");
			}else if(userData[1].equals("2")){
				this.display(userData[0]+" - "+userData[2]+" = ");
				this.display(operation.minus(Integer.parseInt(userData[0]), Integer.parseInt(userData[2]))+"");	
			}else if(userData[1].equals("3")){
				this.display(userData[0]+" X "+userData[2]+" = ");
				this.display(operation.product(Integer.parseInt(userData[0]), Integer.parseInt(userData[2]))+"");	
			}else if(userData[1].equals("4")){
				this.display(userData[0]+" / "+userData[2]+" = ");
				this.display(operation.division(Integer.parseInt(userData[0]), Integer.parseInt(userData[2]))+"");
			}else{
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
		StringBuffer title = new StringBuffer();
		title.append("\n\n\n\n"); //줄바꿈
		title.append("ᕦ(ò_óˇ)ᕤ___________________________________________________________\n");
		title.append("\n"); 
        title.append("                              HoonZzang Calc");
        title.append("\n");
        title.append("                                                Designed by Hz\n");
        title.append(" ____________________________________________________________________\n");
        
		return title.toString(); 
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
	private boolean isNumber(String value) { 
		boolean result = false; 
		try {
			Integer.parseInt(value);
			result = true;
		}catch(Exception e) {	}
		return result;
	}

	// 입력기능
	private String userInput(Scanner sc) {
		return sc.next(); 
	}
 
	// 출력기능
	private void display(String text) { 
		System.out.print(text); 
	}
}
