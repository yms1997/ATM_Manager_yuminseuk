package ATM;

public class Client {
  int clientNo; // 1001부터 자동증가
  String id;
  String pw;
  String name;

  public int getClientNo() {
    return clientNo;
  }

  public String getId() {
    return id;
  }

  public String getPw() {
    return pw;
  }

  public String getName() {
    return name;
  }

  public Client(int clientNo, String id, String pw, String name) {
    this.clientNo = clientNo;
    this.id = id;
    this.pw = pw;
    this.name = name;
  }

  @Override
  public String toString() {
    String data = clientNo + "\t" + id + "\t" + pw + "\t" + name;
    //System.out.println(data);
    return data;
  }

  String saveToData(){
    return "%d/%s/%s/%s\n".formatted(clientNo,id,pw,name);
  }
}
