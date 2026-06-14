Converting `test_report_summary_dark.svg` to PNG

Files:
- `test_report_summary_dark.svg` — vector image of the test summary (dark theme)

Convert locally with Inkscape (recommended):

```bash
# Install Inkscape then:
inkscape app/src/test/resources/test_report_summary_dark.svg --export-type=png --export-filename=app/src/test/resources/test_report_summary_dark.png --export-width=1200 --export-height=800
```

Or use headless Chrome / Chromium to render to PNG:

```bash
# Save this JS as render.js and run with puppeteer or chrome --headless
# Puppeteer example (node):
const puppeteer = require('puppeteer');
(async ()=>{
  const browser = await puppeteer.launch();
  const page = await browser.newPage();
  await page.goto('file://' + process.cwd() + '/app/src/test/resources/test_report_summary_dark.svg');
  await page.screenshot({path: 'app/src/test/resources/test_report_summary_dark.png', fullPage: true});
  await browser.close();
})();
```

Once converted you'll have `test_report_summary_dark.png` which can be pasted into documents or attached to CI artifacts.
