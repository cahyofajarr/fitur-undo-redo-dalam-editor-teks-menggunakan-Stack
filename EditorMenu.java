import java.util.Scanner;

/**
 * Class EditorMenu: tampilan menu untuk fitur Editor Teks (Undo/Redo).
 * Dipisah dari EditorTeks supaya logika (Stack) dan tampilan tidak tercampur.
 */
public class EditorMenu {

	public static void jalankan(EditorTeks editor, Scanner input) {
		int pilihan;

		do {
			System.out.println("\n=== EDITOR TEKS (UNDO/REDO) ===");
			System.out.println("1. Tulis teks");
			System.out.println("2. Hapus karakter terakhir");
			System.out.println("3. Undo");
			System.out.println("4. Redo");
			System.out.println("5. Tampilkan teks & riwayat");
			System.out.println("6. Contoh output (demo)");
			System.out.println("7. Uji waktu eksekusi (nanoTime)");
			System.out.println("0. Kembali");
			System.out.print("Pilih menu: ");

			try {
				pilihan = Integer.parseInt(input.nextLine().trim());
			} catch (NumberFormatException e) {
				pilihan = -1;
			}

			switch (pilihan) {
			case 1:
				System.out.print("Teks yang ditulis (spasi di awal tetap dihitung): ");
				String tambahan = input.nextLine();
				try {
					editor.tulis(tambahan);
					tampilTeks("Teks saat ini", editor);
				} catch (IllegalArgumentException e) {
					System.out.println("Gagal: " + e.getMessage());
				}
				break;
			case 2:
				System.out.print("Jumlah karakter yang dihapus: ");
				try {
					int jumlah = Integer.parseInt(input.nextLine().trim());
					editor.hapus(jumlah);
					tampilTeks("Teks saat ini", editor);
				} catch (NumberFormatException e) {
					System.out.println("Gagal: masukkan angka yang valid.");
				} catch (IllegalArgumentException e) {
					System.out.println("Gagal: " + e.getMessage());
				}
				break;
			case 3:
				if (editor.undo()) {
					tampilTeks("Undo", editor);
				} else {
					System.out.println("Tidak ada yang bisa di-undo.");
				}
				break;
			case 4:
				if (editor.redo()) {
					tampilTeks("Redo", editor);
				} else {
					System.out.println("Tidak ada yang bisa di-redo.");
				}
				break;
			case 5:
				tampilTeks("Teks saat ini", editor);
				System.out.println("Riwayat Undo : " + editor.getJumlahUndo() + " langkah");
				System.out.println("Riwayat Redo : " + editor.getJumlahRedo() + " langkah");
				break;
			case 6:
				demo();
				break;
			case 7:
				ujiWaktu();
				break;
			case 0:
				System.out.println("Kembali ke menu utama.");
				break;
			default:
				System.out.println("Pilihan tidak valid.");
			}
		} while (pilihan != 0);
	}

	private static void tampilTeks(String label, EditorTeks editor) {
		System.out.println(label + ": \"" + editor.getTeks() + "\"");
	}

	/** Contoh output sesuai soal. Memakai editor baru supaya tidak mengganggu teks user. */
	private static void demo() {
		EditorTeks contoh = new EditorTeks();
		contoh.tulis("Selamat");
		contoh.tulis(" datang");

		System.out.println("\n--- Contoh Output untuk Fitur Undo/Redo (Stack) ---");
		tampilTeks("Teks saat ini", contoh);
		contoh.undo();
		tampilTeks("Undo", contoh);
		contoh.redo();
		tampilTeks("Redo", contoh);
	}

	/**
	 * Ukur waktu tulis, undo, dan redo dengan System.nanoTime().
	 * N sengaja dibatasi 1000: tiap tulis() menyimpan satu salinan String di stack,
	 * jadi memori tumbuh cepat kalau N terlalu besar.
	 */
	private static void ujiWaktu() {
		final int N = 1000;
		EditorTeks uji = new EditorTeks();

		long mulai = System.nanoTime();
		for (int i = 0; i < N; i++) {
			uji.tulis("a");
		}
		long waktuTulis = System.nanoTime() - mulai;

		mulai = System.nanoTime();
		while (uji.undo()) {
			// ulangi sampai riwayat undo habis
		}
		long waktuUndo = System.nanoTime() - mulai;

		mulai = System.nanoTime();
		while (uji.redo()) {
			// ulangi sampai riwayat redo habis
		}
		long waktuRedo = System.nanoTime() - mulai;

		System.out.println("\n--- Waktu eksekusi Stack (" + N + " operasi tiap jenis) ---");
		System.out.println("Tulis : " + waktuTulis + " ns total, rata-rata " + (waktuTulis / N) + " ns/operasi");
		System.out.println("Undo  : " + waktuUndo + " ns total, rata-rata " + (waktuUndo / N) + " ns/operasi");
		System.out.println("Redo  : " + waktuRedo + " ns total, rata-rata " + (waktuRedo / N) + " ns/operasi");
		System.out.println("(Angka berubah tiap dijalankan; ambil rata-rata beberapa kali uji.)");
	}
}
