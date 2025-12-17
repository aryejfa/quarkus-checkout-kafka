# Quarkus Checkout Kafka

Layanan yang menerima data dari Kafka topic, melakukan manipulasi data, dan menulisnya kembali ke Kafka serta menyimpannya ke database MySQL.

## Tech Stack

*   **Programming Language**: Java 17
*   **Framework**: Quarkus 3.15.1
*   **Messaging**: Apache Kafka (Redpanda/Confluent via Docker)
*   **Database**: MySQL 8.0 (Localhost / Docker)
*   **Build Tool**: Maven

## Link Video Present Application

```bash
https://jam.dev/c/d185c94e-f27b-4dd9-b070-8111027d63e5
```

## Dataset

Dataset yang digunakan adalah **Data Transaksi E-commerce (Synthetic)**.

Struktur Data (JSON):
```json
{
    "userId": "1",
    "productId": "123",
    "quantity": 2,
    "pricePerUnit": 9.99
}
```

## Fitur Utama

1.  **REST API** (`POST /checkout`): Menerima request transaksi dari luar.
2.  **Database Persistence**: Menyimpan status awal Order ke tabel `orders` di MySQL.
3.  **Kafka Producer**: Mengirim event `OrderCreatedEvent` ke topic `order-created`.
4.  **Kafka Consumer**:
    *   Menerima event `order-created`.
    *   Memproses pembayaran (simulasi logic).
    *   Menyimpan data pembayaran ke tabel `payments` di MySQL.
    *   Mengirim event `PaymentResultEvent` ke topic `payment-result`.
5.  **Order Finalizer**: Update status Order menjadi `COMPLETED` setelah pembayaran sukses.

## Cara Menjalankan

### Prasyarat
*   Java 17+
*   Maven
*   Docker (untuk Kafka)
*   MySQL (Localhost atau Docker)

### 1. Setup Infrastruktur
Jalankan Kafka menggunakan Docker Compose:
```bash
cd docker-kafka
docker-compose up -d
```

### 2. Setup Database
Pastikan MySQL local berjalan di port 3306.
Update kredensial di `src/main/resources/application.properties` jika perlu:
```properties
quarkus.datasource.jdbc.url=jdbc:mysql://localhost:3306/quarkus_checkout_kafka
quarkus.datasource.username=root
quarkus.datasource.password=
```

### 3. Jalankan Aplikasi (Dev Mode)
Gunakan bendera `-Dquarkus.enforceBuildGoal=false` karena struktur module.
```bash
mvn clean quarkus:dev -Dquarkus.enforceBuildGoal=false -DskipTests
```

### 4. Testing dengan cURL
Kirim data transaksi dummy:
```bash
curl -v --location 'http://localhost:8085/checkout' \
--header 'Content-Type: application/json' \
--data '{
    "productId": 123,
    "userId": 1,
    "quantity": 2,
    "pricePerUnit": 9.99
}'
```
