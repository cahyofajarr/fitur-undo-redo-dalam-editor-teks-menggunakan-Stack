import java.util.Scanner;

/**
 * Program utama
 */
public class Main {
	public static void main(String[] args) {
		Queue antrean = new Queue();
		EditorTeks editor = new EditorTeks();
		Scanner input = new Scanner(System.in);
		int pilihan;

		do {
			System.out.println("\n=== SISTEM ANTREAN CUSTOMER SERVICE ===");
			System.out.println("1. Tambah pelanggan");
			System.out.println("2. Layani pelanggan");
			System.out.println("3. Tampilkan antrean");
			System.out.println("4. Editor teks (Undo/Redo)");
			System.out.println("0. Keluar");
			System.out.print("Pilih menu: ");

			try {
				pilihan = Integer.parseInt(input.nextLine().trim());
			} catch (NumberFormatException e) {
				pilihan = -1;
			}

			switch (pilihan) {
			case 1:
				System.out.print("Nama pelanggan : ");
				String nama = input.nextLine();
				System.out.print("Keperluan      : ");
				String keperluan = input.nextLine();
				try {
					Customer c = antrean.tambahPelanggan(nama, keperluan);
					System.out.println("Berhasil ditambahkan dengan nomor antrean " + c.getNomorAntrean());
				} catch (IllegalArgumentException e) {
					System.out.println("Gagal: " + e.getMessage());
				}
				break;
			case 2:
				Customer dilayani = antrean.layaniPelanggan();
				if (dilayani == null) {
					System.out.println("Tidak ada pelanggan dalam antrean.");
				} else {
					System.out.println("Sedang melayani: " + dilayani);
				}
				break;
			case 3:
				antrean.tampilkanAntrean();
				break;
			case 4:
				EditorMenu.jalankan(editor, input);
				break;
			case 0:
				System.out.println("Terima kasih.");
				break;
			default:
				System.out.println("Pilihan tidak valid.");
			}
		} while (pilihan != 0);

		input.close();
	}
}
