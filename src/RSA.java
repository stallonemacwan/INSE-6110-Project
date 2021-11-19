import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class RSA {


    public static long square_multiply(long x, long y, long N) {
        if (y == 0)
            return 1;

        if (y == 1)
            return x % N;

        long tmp = square_multiply(x, y / 2, N);
        tmp = (tmp * tmp) % N;

        if (y % 2 == 0)
            return tmp;
        else
            return ((x % N) * tmp) % N;
    }

    public static Long[] encrypt(long N, int e, String msg) {
        int size = 3;
        String[] parts = msg.split("(?<=\\G.{" + size + "})");
        System.out.println("String values: " + Arrays.toString(parts));
        String[] hexstr = new String[0];
        List<String> l = new ArrayList<String>(Arrays.asList(hexstr));
        for (int i = 0; i < parts.length; i++) {
            char ch[] = parts[i].toCharArray();
            StringBuffer sb = new StringBuffer();
            for (int j = 0; j < ch.length; j++) {
                String hexstring = Integer.toHexString(ch[j]);
                sb.append(hexstring);
            }
            String result = sb.toString();
            l.add(result);
        }
        hexstr = l.toArray(hexstr);
        System.out.println("Hexadecimal values: " + Arrays.toString(hexstr));
        int intarray[] = new int[0];
        List<Integer> la = new ArrayList<Integer>(intarray.length);
        for (int i = 0; i < hexstr.length; i++) {
            la.add(Integer.parseInt(hexstr[i], 16));
        }
        intarray = la.stream().mapToInt(Integer::intValue).toArray();
        System.out.println("Integer Values: " + Arrays.toString(intarray));

        int encrarr[] = new int[0];
        List<Long> enc = new ArrayList<Long>(encrarr.length);
        for (int k = 0; k < intarray.length; k++) {
//            System.out.println(intarray[k]);
            enc.add(square_multiply(intarray[k], e, N) % N);
        }
        Long[] encar = new Long[enc.size()];
        enc.toArray(encar);
//        encrarr = enc.stream().mapToInt(Long::intValue).toArray();
//        System.out.println("Encrypted text is as follows: " +Arrays.toString(encar));
        return encar;

    }

    public static String decrypt(long N, int d, Long[] encr) {
        int decarr[] = new int[0];
        List<Long> dec = new ArrayList<Long>(decarr.length);
        for (int z = 0; z < encr.length; z++) {
//            System.out.println(encrarr[z]);
//            System.out.println(d);
            dec.add(square_multiply(encr[z], d, N));
        }
        decarr = dec.stream().mapToInt(Long::intValue).toArray();
        System.out.println("Decryped Integer values :" + Arrays.toString(decarr));

        String[] dechexstr = new String[0];
        List<String> le = new ArrayList<String>(Arrays.asList(dechexstr));
        for (int i = 0; i < decarr.length; i++) {
            le.add(Integer.toHexString(decarr[i]));
        }
        dechexstr = le.toArray(dechexstr);
        System.out.println("Decrypted Hexadecimal values: " + Arrays.toString(dechexstr));
        String[] decstring = new String[0];
        List<String> str = new ArrayList<String>(Arrays.asList(decstring));
        for (int i = 0; i < dechexstr.length; i++) {
            char[] ch = dechexstr[i].toCharArray();
            String ans = new String();
            for (int j = 0; j < ch.length; j = j + 2) {
                String stri = "" + ch[j] + "" + ch[j + 1];
                char cha = (char) Integer.parseInt(stri, 16);
                ans = ans + cha;
            }
            str.add(ans);
        }
        decstring = str.toArray(decstring);
        System.out.println("Chunk values: " +Arrays.toString(decstring));
//        System.out.println(decstring.getClass().getName());
        return String.join("", str);

    }

    public static boolean verify_signature(long N, int e, Long sign[], String name)
    {
        String verified = decrypt(N, e, sign);
        System.out.println("Partner_name obtained after verification: " + verified);
        Boolean is_valid_signature = (verified.equals(name));
        return  is_valid_signature;
    }


    public static void main(String args[]) {
        int p = 33049;
        int q = 45523;
        int e = 53149;
        long N = 1504489627;
        long Phi = 1504411056;
        int d = 947782645;
        System.out.println("----- My values -----");
        System.out.println("N: " + N);
        System.out.println("Phi(N): " + Phi);
        System.out.println("e: " + e);
        System.out.println("d: " + d);
        ;
        Scanner sc = new Scanner(System.in);
        System.out.println("Before performing all operations");
        System.out.println("Enter your partner's N: ");
        long partner_N = sc.nextLong();
        System.out.println("Enter your partner's e: ");
        int partner_e = sc.nextInt();
        int choice;
        do {
            System.out.println("----- RSA Operations -----");
            System.out.println("1. Encrypt with partner's keys");
            System.out.println("2. Decrypt a message sent to you");
            System.out.println("3. Generate your signature");
            System.out.println("4. Verify your partner's signature");
            System.out.println("5. Exit");
            System.out.println("Choose what you want to do");
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Enter the message you want to send to your partner");
                    sc.nextLine();
                    String message = sc.nextLine();
                    System.out.println("Encrypted values: " + Arrays.toString(encrypt(partner_N, partner_e, message)));
                    break;
                case 2:
                    System.out.println("Enter the encrypted values");
                    sc.nextLine();
                    String encrypted = sc.nextLine();
                    String[] stparts = encrypted.split(", ");
                    Long[] vals = new Long[stparts.length];
                    for (int i = 0; i < stparts.length; i++) {
                        vals[i] = Long.parseLong(stparts[i]);
                    }
                    System.out.println("Message sent to you is " + decrypt(N, d, vals));
                    break;
                case 3:
                    System.out.println("Enter your name");
                    sc.nextLine();
                    String name = sc.nextLine();
                    System.out.println("Your Signature: " + Arrays.toString(encrypt(N, d, name)));
                    break;
                case 4:
                    System.out.println("Enter your partner's signature");
                    sc.nextLine();
                    String partner_signature = sc.nextLine();
                    System.out.println("Enter your partner's name to verify");
                    String partner_name = sc.nextLine();
                    String[] stparts2 = partner_signature.split(", ");
                    Long[] vals2 = new Long[stparts2.length];
                    for (int i = 0; i < stparts2.length; i++) {
                        vals2[i] = Long.parseLong(stparts2[i]);
                    }
                    System.out.println("Signature verification algorithm returned: " +verify_signature(partner_N,partner_e,vals2,partner_name));
                    break;
                case 5:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Choose from the above options.");
            }
        } while (choice != 5);
    }

}


