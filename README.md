# 🖥️ OpsMonitor - IT Infrastructure Monitoring System

**OpsMonitor** adalah aplikasi Android berbasis Kotlin yang dirancang untuk memantau status dan kesehatan infrastruktur server / data center secara *real-time*. Aplikasi ini membantu *System Administrator* dan tim *IT Operations* dalam memantau penggunaan resource (CPU, RAM, Disk), status server (*Online, Warning, Offline*), serta melakukan aksi operasional cepat secara efisien.

---

## ✨ Fitur Utama

### 🔐 1. Autentikasi Pengguna
* **Login & Register Screen:** Tampilan bertema *Dark Mode* modern (#0B1120) khusus IT Operations.
* **Sesi Logout:** Opsi keluar dari akun pada halaman Dashboard.

### 📊 2. Dashboard Pemantauan
* **Ringkasan Telemetri:** Menampilkan jumlah total server serta pembagian status (*Online*, *Warning*, dan *Offline*).
* **Grafik Performa Live:** Visualisasi tren penggunaan CPU & RAM menggunakan grafik interaktif MPAndroidChart.
* **Akses Cepat:** Tombol navigasi langsung ke halaman daftar server.

### 🖥️ 3. Manajemen & Filter Daftar Server
* **Pencarian Real-time:** Mencari server berdasarkan nama *hostname* atau *Alamat IP*.
* **Filter Chip Status:** Penyaringan instan berdasarkan kategori `[Semua]`, `[Online]`, `[Warning]`, dan `[Offline]`.
* **Tampilan Empty State:** Indikator visual apabila server yang dicari tidak ditemukan.

### ⚙️ 4. Detail Server & Aksi Operasional
* **Metrics Resource Lengkap:** Menampilkan performa terkini CPU, RAM, Disk Usage.
* **Informasi Data Center:** Menampilkan lokasi Data Center dan posisi Rack (Space U-Space).
* **Grafik Historis:** Visualisasi historis performa server pilihan.
* **Aksi Cepat (Quick Actions):**
  * 📋 **Salin IP:** Salin IP Address ke clipboard pengguna secara cepat.
  * 🔄 **Reboot Server:** Konfirmasi dialog keamanan sebelum melakukan reboot server.

### 🧭 5. Navigasi & Tampilan
* **Jetpack Navigation Component:** Navigasi fragment yang mulus dan terstruktur.
* **Bottom Navigation Bar:** Berpindah menu dengan fleksibel & otomatis tersembunyi saat berada di layar Detail Server.

---

## 🛠️ Teknologi & Library yang Digunakan

* **Bahasa:** [Kotlin](https://kotlinlang.org/)
* **Minimum SDK:** API 24 (Android 7.0 Nougat)
* **Target SDK:** API 36 / Android 15
* **UI & Component:**
  * ViewBinding
  * Material Design 3 (Google Material Components)
  * ConstraintLayout & NestedScrollView
* **Navigasi:** Android Jetpack Navigation Component
* **Grafik / Chart:** [MPAndroidChart v3.1.0](https://github.com/PhilJay/MPAndroidChart)

---

## 🚀 Cara Menjalankan Project

1. **Clone Repository:**
   ```bash
   git clone https://github.com/username-kamu/OpsMonitor.git
   ```
2. **Buka di Android Studio:**
   * Buka Android Studio $\rightarrow$ `File` $\rightarrow$ `Open...` $\rightarrow$ Pilih folder `OpsMonitor`.
3. **Sync Gradle:**
   * Tunggu hingga proses *Gradle Sync* selesai (pastikan koneksi internet aktif untuk mendownload library MPAndroidChart dari JitPack).
4. **Jalankan Aplikasi:**
   * Pilih Emulator atau Perangkat Fisik (Physical Device), lalu tekan tombol **Run** (`Shift + F10`).

---

## 📁 Struktur Direktori Project

```text
com.example.opsmonitor
 ├── adapter
 │    └── ServerAdapter.kt          # Adapter RecyclerView Daftar Server
 ├── model
 │    └── Server.kt                 # Data Model Server (Serializable)
 ├── ui
 │    ├── DashboardFragment.kt      # Fragment Dashboard Pemantauan
 │    ├── LoginActivity.kt          # Activity Login
 │    ├── RegisterActivity.kt       # Activity Register
 │    ├── ServerDetailFragment.kt   # Fragment Detail Server & Grafik Metrics
 │    └── ServerListFragment.kt     # Fragment Daftar Server & Status Filter
 └── MainActivity.kt                # Activity Utama & NavHost
```

---

## 📝 Lisensi

Project ini dibuat untuk keperluan pembelajaran dan pengembangan aplikasi Android Fundamental.  
Developed with ❤️ by **Aditya Rizkiawan Nugraha**.
