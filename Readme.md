# Aplikasi Manajemen Data Pegawai

Aplikasi ini adalah aplikasi sederhana untuk manajemen data pegawai. Aplikasi ini memungkinkan Anda untuk menambahkan, memperbarui, melihat, dan menghapus data pegawai.

## Fitur

- **Conn**: Kelas ini bertanggung jawab untuk mengatur koneksi ke database. Ini adalah bagian penting dari aplikasi karena semua operasi data (menambahkan, memperbarui, melihat, dan menghapus data pegawai) memerlukan koneksi ke database.
- **Splash**: Kelas ini digunakan untuk menampilkan splash screen saat aplikasi pertama kali dijalankan. Splash screen biasanya digunakan untuk menampilkan logo atau informasi lainnya sementara aplikasi sedang dimuat.
- **Home**: Kelas ini bertindak sebagai halaman utama aplikasi. Biasanya, halaman ini akan menampilkan menu atau navigasi ke fitur-fitur lainnya dalam aplikasi.
- **Login**: Kelas ini digunakan untuk melakukan verifikasi pengguna. Pengguna harus memasukkan kredensial mereka (biasanya username dan password) dan sistem akan memeriksa apakah kredensial tersebut valid.
- **Sign Up**: Kelas ini digunakan untuk mendaftarkan pengguna baru ke sistem. Pengguna harus memberikan informasi yang diperlukan (seperti username, password, email, dll.) untuk membuat akun baru.
- **AddEmployee**: Kelas ini digunakan untuk menambahkan data pegawai baru ke database. Pengguna dapat memasukkan detail pegawai seperti nama, tanggal lahir, SSN, alamat, jenis kelamin, gaji, dan departemen.
- **UpdateEmployee**: Kelas ini digunakan untuk memperbarui data pegawai yang ada di database. Pengguna dapat memilih pegawai tertentu dan memperbarui detail mereka.
- **ViewEmployee**: Kelas ini digunakan untuk melihat data pegawai yang telah tersimpan di database. Pengguna dapat melihat detail semua pegawai atau mencari pegawai tertentu.
- **RemoveEmployee**: Kelas ini digunakan untuk menghapus data pegawai dari database. Pengguna dapat memilih pegawai tertentu dan menghapus mereka dari sistem.

## Cara Menggunakan

1. Clone repositori ini dengan menggunakan perintah berikut di terminal Anda:
    ```
    git clone https://github.com/EgoWidiarto/Employee-Management-System.git
    ```
2. Buka proyek di IDE pilihan Anda.
3. Pastikan Anda telah mengatur koneksi database dengan benar di kelas Conn.
4. Jalankan aplikasi.

## Kontribusi

Kontribusi dipersilakan. Untuk kontribusi besar, silakan buka issue terlebih dahulu untuk membahas apa yang ingin Anda ubah.
