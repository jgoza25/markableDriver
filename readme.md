# 検証個所自動マーク付きWebDriver

SeleniumのWebDriverを拡張し、画面キャプチャ時に検証個所にマークを追加します。

**特徴**
* findElement(s)を実行した要素を自動的に赤線で囲み、画面キャプチャに出力します。
* WebDriverの生成処理を書き換えるだけで機能します。
* 要素に対してコメントの記述が可能です。

## ダウンロード

最新バージョン　[markableWebDriver_0.1.jar](dist/markableWebDriver_0.1.jar)

## 使い方
WebDriverの生成箇所を下記のように書き換えます。
```java
		// WebDriver driver = new FirefoxDriver();
		WebDriver driver = new MarkableWebDriver(new FirefoxDriver());
```

## 適用前の画面キャプチャ
![view2](res/00b.png)

## 適用後の画面キャプチャ
![view3](res/00.png)

