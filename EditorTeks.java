import java.util.Stack;

/**
 * Class EditorTeks: editor teks sederhana dengan fitur Undo/Redo.
 * Memakai dua Stack (LIFO, Last In First Out):
 *  - undoStack : menyimpan kondisi teks SEBELUM setiap perubahan
 *  - redoStack : menyimpan kondisi teks yang baru saja di-undo
 */
public class EditorTeks {

	private String teks = "";
	private final Stack<String> undoStack = new Stack<>();
	private final Stack<String> redoStack = new Stack<>();

	/** Getter: teks yang sedang tampil di editor. */
	public String getTeks() {
		return teks;
	}

	/**
	 * Setter: mengganti seluruh teks. Ini SATU-SATUNYA tempat teks berubah
	 * karena perubahan baru, jadi riwayat pasti selalu tercatat di sini.
	 */
	public void setTeks(String teksBaru) {
		if (teksBaru == null) {
			throw new IllegalArgumentException("Teks tidak boleh null");
		}
		undoStack.push(teks); // simpan kondisi lama
		redoStack.clear();    // perubahan baru = riwayat redo lama tidak berlaku lagi
		teks = teksBaru;
	}

	/** Fitur 1: tambah teks di akhir. */
	public void tulis(String tambahan) {
		if (tambahan == null || tambahan.isEmpty()) {
			throw new IllegalArgumentException("Teks yang ditulis tidak boleh kosong");
		}
		setTeks(teks + tambahan);
	}

	/** Fitur 2: hapus sejumlah karakter dari akhir teks. */
	public void hapus(int jumlah) {
		if (jumlah <= 0) {
			throw new IllegalArgumentException("Jumlah karakter harus lebih dari 0");
		}
		if (teks.isEmpty()) {
			throw new IllegalArgumentException("Teks masih kosong, tidak ada yang dihapus");
		}
		int sisa = Math.max(0, teks.length() - jumlah);
		setTeks(teks.substring(0, sisa));
	}

	/** Fitur 3: Undo, kembali ke kondisi sebelumnya. false jika tidak ada riwayat. */
	public boolean undo() {
		if (undoStack.isEmpty()) {
			return false;
		}
		redoStack.push(teks);    // simpan kondisi sekarang agar bisa di-redo
		teks = undoStack.pop();  // ambil kondisi sebelumnya
		return true;
	}

	/** Fitur 4: Redo, membatalkan Undo terakhir. false jika tidak ada yang bisa di-redo. */
	public boolean redo() {
		if (redoStack.isEmpty()) {
			return false;
		}
		undoStack.push(teks);    // simpan kondisi sekarang agar bisa di-undo lagi
		teks = redoStack.pop();
		return true;
	}

	public boolean isUndoKosong() {
		return undoStack.isEmpty();
	}

	public boolean isRedoKosong() {
		return redoStack.isEmpty();
	}

	public int getJumlahUndo() {
		return undoStack.size();
	}

	public int getJumlahRedo() {
		return redoStack.size();
	}
}
