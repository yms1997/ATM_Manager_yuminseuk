package ATM;

import java.util.ArrayList;

public class ClientDAO {
  ArrayList<Client> cliList;
  Util ut;
  int maxNo;
  Client log;

  ClientDAO(){
    ut = new Util();
    cliList = new ArrayList<>();
    maxNo = 1001;
  }

  void addClientsFromData(String data){ // 저장된파일 나눠서 배열에 넣기
    if(data.isEmpty()) return;
    String[] temp = data.split("\n");
    for (int i = 0; i < temp.length; i++) {
      String[] info = temp[i].split("/");
      cliList.add(new Client(Integer.parseInt(info[0]), info[1], info[2], info[3]));
    }
    maxNo = updateMaxStuNo();
  }

  int updateMaxStuNo() { // 최신번호화
    if(cliList.size() == 0) return maxNo;
    int maxNo = 0;
    for(Client c : cliList) {
      if(maxNo < c.clientNo) {
        maxNo = c.clientNo;
      }
    }
    return maxNo;
  }

  boolean hasClientData(){ // 데이터유무 확인
    if(cliList.size() == 0){
      System.out.println("[ No Client Data ]");
      return false;
    }
    return true;
  }

  String saveAsFileData() { // 파일저장
    if(cliList.size() == 0) return "";
    String data = "";
    for(Client cl : cliList) {
      data += cl.saveToData();
    }
    return data;
  }

  void addOneClient(){ // 회원가입
    String newId = ut.getValue("[추가] id 입력 : ");
    Client c = getOneClientbyId(newId);
    if(c != null){
      System.out.println("중복된 id입니다");
      return;
    }
    String newPw = ut.getValue("[추가] pw 입력 : ");
    String newName = ut.getValue("[추가] 이름 입력 : ");
    Client client = new Client(++maxNo, newId, newPw, newName);
    cliList.add(client);
    System.out.println("회원가입 완료!");
  }

  Client getOneClientbyId(String id){ // 아이디 찾기
    if(cliList.size() == 0) return null;
    for (Client c : cliList) {
      if(c.id.equals(id)){
        return c;
      }
    }
    return null;
  }

  boolean loginClient(){ // 로그인
    if(!hasClientData()) return false;
    String Id = ut.getValue("[로그인] id ");
    Client c = getOneClientbyId(Id);
    if(c == null){
      System.out.println("없는 아이디 입니다");
      return false;
    }
    String Pw = ut.getValue("[로그인] pw ");
    if(Pw.equals(c.pw)){
      System.out.println("[ 로그인 성공 ]");
      log = c;
      return true;
    }
    else {
      System.out.println("[ 로그인 실패 ]");
      return false;
    }

  }

  int getIdIdx(Client c){ // 인덱스찾기
    if(cliList.size() == 0) return -1;
    for (Client client : cliList) {
      if(client.id.equals(c.id)){
        return cliList.indexOf(c);
      }
    }
    return -1;
  }

  boolean checkPw(int idx, String Pw){ // 비밀번호검사
    if(cliList.get(idx).pw.equals(Pw)){
      return true;
    }
    return false;
  }

  void printAllClient(){ // 회원 전체조회
    for (Client c : cliList) {
        System.out.println(c.toString());
    }
  }

  void changePwandName(){ // 회원 수정
    String Id = ut.getValue("[수정] id 입력 : ");
    Client c = getOneClientbyId(Id);
    if(getIdIdx(c) == -1){
      System.out.println("없는 id 입니다");
      return;
    }
    String changePw = ut.getValue("[수정] pw 입력 : ");
    if(checkPw(getIdIdx(c), changePw)){
      System.out.println("다른 비밀번호 입력");
      return;
    }
    String changeName = ut.getValue("[수정] 이름 입력 : ");
   cliList.get(getIdIdx(c)).pw = changePw;
   cliList.get(getIdIdx(c)).name = changeName;
    System.out.println("회원 수정 완료!");
  }

  void delOneClient(AccountDAO acc){ // 회원 삭제
    if(!hasClientData()) return;
    String Id = ut.getValue("[삭제] id 입력 : ");
    Client c = getOneClientbyId(Id);
    if(c == null){
      System.out.println("제대로 된 id 입력");
      return;
    }
    cliList.remove(c);
    acc.deleteAllAccountsInOneClient(c);
    System.out.println("회원 삭제 완료!");
  }

  boolean deleteLogClient(AccountDAO aDAO){
    String pw = ut.getValue("비밀번호");
    if(!log.pw.equals(pw)){
      System.out.println("비밀번호가 일치하지 않습니다");
      return false;
    }
    int delIdx = getIdIdx(log);
    cliList.remove(delIdx);
    aDAO.deleteAllAccountsInOneClient(log);
    System.out.println(log + "\n [ 회원 삭제 완료 ]");
    log = null;
    return true;
  }

}
