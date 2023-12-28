package ATM;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Util {
  Scanner sc = new Scanner(System.in);

  final String CUR_PATH = System.getProperty("user.dir") + "\\ATM_Manager_yuminseuk\\ATM_Manager_yuminseuk\\" + this.getClass().getPackageName() + "\\";

  public Util() {}

  public Util(int i) {
    tempData();
  }

  int getValue(String msg, int start, int end) {
    while (true) {
      System.out.printf("▶ %s[ %d - %d ] 입력:", msg, start, end);
      try {
        int num = sc.nextInt();
        sc.nextLine();
        if (num < start || num > end) {
          System.out.printf("%d - %d 사이값 입력해주세요 %n", start, end);
          continue;
        }
        return num;
      } catch (Exception e) {
        sc.nextLine();
        System.out.println("숫자값을 입력하세요");
      }
    }
  }

  String getValue(String msg) {
    System.out.printf("▶ %s 입력: ", msg);
    return sc.next();
  }

  String loadData(String fileName) {

    String data = "";
    try (FileReader fr = new FileReader(CUR_PATH + fileName); BufferedReader br = new BufferedReader(fr)) {
      while (true) {
        String line = br.readLine();
        if (line == null) {
          break;
        }
        data += line + "\n";
      }
      data = data.substring(0, data.length() - 1);
      //System.out.println(fileName + " 로드 완료");

    } catch (IOException e) {
      System.out.println(fileName + " 로드 실패");
      e.printStackTrace();
    }
    return data;
  }

  void loadFromFile(AccountDAO accDAO, ClientDAO cliDAO) {
    String accData = loadData("accData.txt");
    String cliData = loadData("cliData.txt");

    accDAO.addAccountsFromData(accData);
    cliDAO.addClientsFromData(cliData);
    cliDAO.updateMaxStuNo();
  }
  void saveToFile(AccountDAO accDAO, ClientDAO cliDAO){
    String accData = accDAO.saveAsFileData();
    String cliData = cliDAO.saveAsFileData();
    saveData("accData.txt", accData);
    saveData("cliData.txt", cliData);
  }

  void saveData(String fileName, String data) {
    try(FileWriter fw = new FileWriter(CUR_PATH+fileName)){
      fw.write(data);
      System.out.println(fileName + "저장 성공");
    } catch (IOException e) {
      System.out.println(fileName + "저장 실패");
      e.printStackTrace();
    }
  }

  void tempData(){

    String userdata = "1001/test01/pw1/김철수\n";
    userdata += "1002/test02/pw2/이영희\n";
    userdata += "1003/test03/pw3/신민수\n";
    userdata += "1004/test04/pw4/최상민";
    saveData("cliData.txt",userdata);

    String accountdata = "test01/1111-1111-1111/8000\n";
    accountdata += "test02/2222-2222-2222/5000\n";
    accountdata += "test01/3333-3333-3333/11000\n";
    accountdata += "test03/4444-4444-4444/9000\n";
    accountdata += "test01/5555-5555-5555/5400\n";
    accountdata += "test02/6666-6666-6666/1000\n";
    accountdata += "test03/7777-7777-7777/1000\n";
    accountdata += "test04/8888-8888-8888/1000\n";

    saveData("accData.txt", accountdata);
  }

}
