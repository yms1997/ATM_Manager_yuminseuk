package ATM;

public class AccountDAO {
  Account[] accList;
  int cnt;
  Util ut;

  AccountDAO(){
      ut = new Util();
  }

  void addAccountsFromData(String data){
      String[] temp = data.split("\n");
      accList = new Account[temp.length];
      cnt = temp.length;
      for (int i = 0; i < cnt; i++) {
        String[] info = temp[i].split("/");
        Account acc = new Account(info[0],info[1],Integer.parseInt(info[2]));
        accList[i] = acc;
      }
  }

  String saveAsFileData() {
    if(cnt == 0) return "";
    String data = "";
    for(Account ac : accList) {
      data += ac.saveToData();
    }
    return data;
  }

  void insertAccount(Account acc){
    if(cnt == 0){
      accList = new Account[cnt + 1];
    }
    else{
      Account[] temp = accList;
      accList = new Account[cnt + 1];
      int idx = 0;
      for (int i = 0; i < cnt; i++) {
        accList[idx++] = temp[i];
      }
    }
    accList[cnt] = acc;
    cnt += 1;
  }

  Account getOneAccountbyId(String id){
    if(cnt == 0) return null;
    for (Account a : accList) {
      if(id.equals(a.clientId)){
        return a;
      }
    }
    return null;
  }

  void deleteAllAccountsInOneClient(Client cli){
    for (int i = 0; i < cnt; i++) {
      if(cli.id == accList[i].clientId){
        deleteAccount(i);
      }
    }
  }

  void deleteAccount(int delidx){
    if(cnt == 1){
      accList = null;
      cnt = 0;
      return;
    }
    Account[] temp = accList;
    accList = new Account[cnt - 1];
    int index = 0;
    for (int i = 0; i < cnt; i++) {
      if(i != delidx){
        accList[index++] = temp[i];
      }
    }
    cnt -= 1;
  }

  void printAllAccount(){
    for (Account a : accList) {
      System.out.println(a.toString());
    }
  }

}

