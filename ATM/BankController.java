package ATM;

public class BankController {
    final String bankName = "우리은행";
    AccountDAO accDAO;
    ClientDAO cliDAO;
    Util ut;
    String log;

    BankController() {
        accDAO = new AccountDAO();
        cliDAO = new ClientDAO();
        ut = new Util(1);
        ut.loadFromFile(accDAO,cliDAO);
    }
    // 첫시작메뉴
    // [1]관리자 [2]사용자 [0]종료

    // 관리자메뉴
    // [1]회원목록 [2]회원수정 [3]회원삭제 [4]데이터저장 [5]데이터 불러오기

    // 회원 수정 : 비밀번호, 이름 수정가능
    // 회원 삭제 : 회원아이디 검색
    // 데이터 저장 : account.txt, client.txt

    // 사용자메뉴
    // [1]회원가입 [2] 로그인 [0]뒤로가기

    // 회원가입 :  회원 아이디 중복 확인

    // 로그인 후 메뉴
    // [1]계좌추가 [2]계좌삭제 [3]입금 [4]출금 [5]이체 [6]탈퇴 [7]마이페이지 [0]로그아웃
    // 계좌 추가(숫자4개 - 숫자4개 - 숫자4개) 일치할때 추가가능 : 중복확인
    // 계좌 삭제 : 본인 회원 계좌만 가능
    // 입금 : accList 계좌가 있을때만 입금 가능 (100원 이상 입금/이체/출금) (계좌 잔고만큼만)
    // 이체 : 이체할 계좌랑 이체받을 계좌만 일치 안하면 됨
    // 탈퇴 : 패스워드 다시 입력 -> 탈퇴가능
    // 마이페이지 : 내 계좌목록 확인 -> 인덱스 계좌 선택 -> 계좌 비번 변경 가능

    void mainMenu(){
        while (true){
            System.out.println("==[" + bankName + " ATM]==");
            System.out.println("[1]관리자 [2]사용자 [0]종료");
            int sel = ut.getValue("메뉴 입력",0 ,2);
            if(sel == 0){
                System.out.println("프로그램 종료");
                return;
            } else if (sel == 1) {
                adminMenu();
            } else if (sel == 2) {
                userMenu();
            }
        }
    }
    void adminMenu(){
        while (true){
            System.out.println("==[" + bankName + " 관리자]==");
            System.out.println("[1]회원목록 [2]회원수정 [3]회원삭제 [4]데이터저장 [5]데이터 불러오기 [6]전체계좌목록 [0]되돌아가기");
            int sel = ut.getValue("메뉴 입력",0, 6);
            if(sel == 0){
                return;
            } else if (sel == 1) {
                cliDAO.printAllClient();
            } else if (sel == 2) {
                cliDAO.changePwandName();
            } else if (sel == 3) {
                cliDAO.delOneClient(accDAO);
            } else if (sel == 4) {
                ut.saveToFile(accDAO,cliDAO);
            } else if (sel == 5) {
                ut.loadFromFile(accDAO,cliDAO);
            } else if (sel == 6) {
                accDAO.printAllAccount();
            }
        }

    }
    void userMenu(){
        while (true){
            System.out.println("[1]회원가입 [2] 로그인 [0]뒤로가기");
            int sel = ut.getValue("메뉴 입력",0, 2);
            if(sel == 0){
                return;
            } else if (sel == 1) {
                cliDAO.addOneClient();
            } else if (sel == 2) {
                log = cliDAO.loginClient();
                if(!log.equals("")){
                    loginuserMenu();
                }
            }
        }
    }
    void loginuserMenu(){
        while(true){
            System.out.println("[1]계좌추가 [2]계좌삭제 [3]입금 [4]출금 [5]이체 [6]탈퇴 [7]마이페이지 [0]로그아웃");
            int sel = ut.getValue("메뉴 입력",0, 7);
            if(sel == 0){
                log = "";
                return;
            } else if (sel == 1) {
                
            } else if (sel == 2) {
                
            } else if (sel == 3) {
                
            } else if (sel == 4) {
                
            } else if (sel == 5) {
                
            } else if (sel == 6) {
                
            } else if (sel == 7) {
                
            }

        }
        
    }
    void run(){
        mainMenu();
    }
}
