package ATM;

public class ClientDAO {
  Client[] cliList;
  int cnt;
  Util ut;
  int maxNo;

  ClientDAO(){
    ut = new Util();
    maxNo = 1001;
  }

  Client getOneClientbyId(String id){
    if(cnt == 0) return null;
    for (Client c : cliList) {
      if(id.equals(c.id)){
        return c;
      }
    }
    return null;
  }

  int getIdIdx(Client cli){
    if(cliList == null) return -1;
    for (int i = 0; i < cnt; i++) {
      if(cli == cliList[i]){
        return i;
      }
    }
    return -1;
  }
  boolean checkPw(int idx, String pw){
    if(cliList[idx].pw.equals(pw)){
      return true;
    }
    return false;
  }

  void addClientsFromData(String data){
    String[] temp = data.split("\n");
    cliList = new Client[temp.length];
    cnt = temp.length;
    for (int i = 0; i < cnt; i++) {
      String[] info = temp[i].split("/");
      Client cl = new Client(Integer.parseInt(info[0]),info[1],info[2],info[3]);
      cliList[i] = cl;
    }
  }

  boolean hasClientData(){
    if(cliList == null){
      System.out.println("데이터가 존재하지 않습니다");
      return false;
    }
    return true;
  }

  String saveAsFileData() {
    if(cnt == 0) return "";
    String data = "";
    for(Client cl : cliList) {
      data += cl.saveToData();
    }
    return data;
  }

  void updateMaxStuNo() {
    if(cnt == 0) return;
    int maxNo = 0;
    for(Client c : cliList) {
      if(maxNo < c.clientNo) {
        maxNo = c.clientNo;
      }
    }
    this.maxNo = maxNo;
  }

  void addOneClient(){ // 회원가입
    String newId = ut.getValue("[추가] id 입력 : ");
    Client c = getOneClientbyId(newId);
    if(c != null){
      System.out.println("중복된 id 입니다");
      return;
    }
    String newPw = ut.getValue("[추가] pw 입력 : ");
    String newName = ut.getValue("[추가] 이름 입력 : ");
    Client cli = new Client(++maxNo,newId,newPw,newName);
    System.out.println(cli);
    insertClient(cli);
    System.out.println("회원가입 완료!");
  }

  void insertClient(Client cli){
    if(cnt == 0){
      cliList = new Client[cnt + 1];
    }
    else{
      Client[] temp = cliList;
      cliList = new Client[cnt + 1];
      int idx = 0;
      for (int i = 0; i < cnt; i++) {
        cliList[idx++] = temp[i];
      }
      cliList[cnt] = cli;
      cnt += 1;
    }
  }

  String loginClient(){ // 로그인
    String Id = ut.getValue("[로그인] id ");
    Client c = getOneClientbyId(Id);
    String Pw = ut.getValue("[로그인] pw ");
    if(getIdIdx(c) == -1 || !checkPw(getIdIdx(c),Pw)){
      System.out.println("id 및 pw 확인");
      return "";
    }

    return c.id;
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
    cliList[getIdIdx(c)].pw = changePw;
    cliList[getIdIdx(c)].name = changeName;
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
    int idx = getIdIdx(c);
    removeClient(idx);
    acc.deleteAllAccountsInOneClient(c);
    System.out.println(c + "회원 삭제 완료!");
  }

  void removeClient(int idx){
    if(cnt == 1){
      cliList = null;
      cnt = 0;
      return;
    }
    Client[] temp = cliList;
    cliList = new Client[cnt - 1];
    int index = 0;
    for (int i = 0; i < cnt; i++) {
      if(i != idx){
        cliList[index++] = temp[i];
      }
    }
    cnt -= 1;
  }
}
