# TaipeiZooMVP

> 透過[台北資料大平臺][台北資料大平臺]取得台北動物園館區及植物資料，並以MVP架構實作。

### 實作方式

* MVP架構
* 使用[Koin][Koin]實現dependency injection
* 使用View Binding
* 使用RxJava實現API的呼叫，並依照館區名稱過濾出現的植物資料

### 遇到的問題點
1. 圖片網址為http，因此將Glide內的OKHttp Client元件改以接收任何憑證都允許的方式
1. 在Server回傳植物資料時，植物的中文名稱的Key值若是以`F_Name_Ch`所取得的資料為null，有發現其完整的Key值應該是`\uFEFFF_Name_Ch`，將SerializedName更改為`\uFEFFF_Name_Ch`即可正常取得資料。

[台北資料大平臺]: https://data.taipei/
[Koin]: https://insert-koin.io/
