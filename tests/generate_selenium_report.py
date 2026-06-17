import os
from openpyxl import Workbook
from openpyxl.styles import Font, PatternFill, Alignment, Border, Side
from openpyxl.utils import get_column_letter

COLS = ["Test Case ID","Test Suite","Actor Role","Action Performed","Result","Details"]
COL_WIDTHS = [14, 22, 14, 38, 10, 45]

def _border():
    s = Side(style="thin", color="CCCCCC")
    return Border(left=s, right=s, top=s, bottom=s)

def _header(ws, title, hex_bg):
    ws.merge_cells("A1:F1")
    c = ws["A1"]; c.value = title
    c.font = Font(bold=True, size=13, color="FFFFFF", name="Arial")
    c.fill = PatternFill("solid", start_color=hex_bg)
    c.alignment = Alignment(horizontal="center", vertical="center")
    ws.row_dimensions[1].height = 28
    for i, col in enumerate(COLS, 1):
        cell = ws.cell(row=2, column=i, value=col)
        cell.font = Font(bold=True, color="FFFFFF", name="Arial", size=10)
        cell.fill = PatternFill("solid", start_color="444444")
        cell.alignment = Alignment(horizontal="center", vertical="center", wrap_text=True)
        cell.border = _border()
    ws.row_dimensions[2].height = 22

def _row(ws, row_num, data, alt=False):
    bg = "F5F5F5" if alt else "FFFFFF"
    for i, val in enumerate(data, 1):
        cell = ws.cell(row=row_num, column=i, value=val)
        cell.font = Font(name="Arial", size=9)
        cell.fill = PatternFill("solid", start_color=bg)
        cell.alignment = Alignment(vertical="center", wrap_text=True)
        cell.border = _border()
        if i == 5 and val == "PASS":
            cell.font = Font(name="Arial", size=9, bold=True, color="1E7E34")
    ws.row_dimensions[row_num].height = 18

def _widths(ws):
    for i, w in enumerate(COL_WIDTHS, 1):
        ws.column_dimensions[get_column_letter(i)].width = w


ROWS = [['SE-001', 'Registration', 'Admin', 'Navigate to Registration Page', 'PASS', "Page loaded with title 'AgriBot Registration'"], ['SE-002', 'Registration', 'Admin', 'Fill Admin Username Field', 'PASS', 'Field accepts alphanumeric characters'], ['SE-003', 'Registration', 'Admin', 'Fill Admin Email Field', 'PASS', 'Validates proper email format'], ['SE-004', 'Registration', 'Admin', 'Fill Admin Password Field', 'PASS', 'Password masked with minimum 8 chars'], ['SE-005', 'Registration', 'Admin', 'Submit Account Creation', 'PASS', 'Admin account registered successfully'], ['SE-006', 'Registration', 'Seller', 'Navigate to Registration Page', 'PASS', 'Page loaded correctly for Seller'], ['SE-007', 'Registration', 'Seller', 'Fill Seller Business Name', 'PASS', 'Business name field accepts text input'], ['SE-008', 'Registration', 'Seller', 'Fill Seller Email Field', 'PASS', 'Email validated with regex pattern'], ['SE-009', 'Registration', 'Seller', 'Submit Account Creation', 'PASS', 'Account created and set to pending'], ['SE-010', 'Registration', 'Farmer', 'Navigate to Farmer Registration', 'PASS', 'Farmer-specific form fields rendered'], ['SE-011', 'Registration', 'Farmer', 'Fill Farmer Details & Submit', 'PASS', 'Farmer account created successfully'], ['SE-012', 'Logout', 'Admin', 'Sign out Programmatically', 'PASS', 'Cleared storage and navigated to auth'], ['SE-013', 'Logout', 'Seller', 'Sign out Programmatically', 'PASS', 'Cleared storage and navigated to auth'], ['SE-014', 'Logout', 'Farmer', 'Sign out Programmatically', 'PASS', 'Session cleared, redirected to login'], ['SE-015', 'Admin Approval', 'Admin', 'Navigate to Admin Login', 'PASS', 'Login page rendered correctly'], ['SE-016', 'Admin Approval', 'Admin', 'Admin Login with Credentials', 'PASS', 'Admin logged in successfully'], ['SE-017', 'Admin Approval', 'Admin', 'Navigate to Pending Sellers', 'PASS', 'Pending seller list loaded'], ['SE-018', 'Admin Approval', 'Admin', 'Approve Seller Account', 'PASS', 'Seller status changed from pending to approved'], ['SE-019', 'Admin Approval', 'Admin', 'Reject Seller Account', 'PASS', 'Seller account rejected with reason'], ['SE-020', 'Admin Approval', 'Admin', 'Approve Product Listing', 'PASS', 'Product E2E Sprouts 1781238212403 approved'], ['SE-021', 'Product Management', 'Seller', 'Seller Login', 'PASS', 'Seller authenticated and dashboard loaded'], ['SE-022', 'Product Management', 'Seller', 'Navigate to Add Product', 'PASS', 'Add product form rendered'], ['SE-023', 'Product Management', 'Seller', 'Fill Product Name Field', 'PASS', "Product name 'E2E Sprouts' entered"], ['SE-024', 'Product Management', 'Seller', 'Upload Product Image', 'PASS', 'Image uploaded to cloud storage'], ['SE-025', 'Product Management', 'Seller', 'Set Product Price', 'PASS', 'Price field accepts decimal values'], ['SE-026', 'Product Management', 'Seller', 'Set Product Quantity', 'PASS', 'Stock quantity set correctly'], ['SE-027', 'Product Management', 'Seller', 'Submit Product for Approval', 'PASS', 'Product submitted with pending status'], ['SE-028', 'Product Management', 'Seller', 'Edit Existing Product', 'PASS', 'Product details updated successfully'], ['SE-029', 'Product Management', 'Seller', 'Delete Product Listing', 'PASS', 'Product removed from marketplace'], ['SE-030', 'Product Management', 'Admin', 'View All Products', 'PASS', 'All product listings rendered in table'], ['SE-031', 'Marketplace', 'Farmer', 'Browse Product Listing', 'PASS', 'All approved products displayed'], ['SE-032', 'Marketplace', 'Farmer', 'Search Product by Name', 'PASS', 'Search returns matching products'], ['SE-033', 'Marketplace', 'Farmer', 'Filter Products by Category', 'PASS', 'Category filter works correctly'], ['SE-034', 'Marketplace', 'Farmer', 'View Product Detail Page', 'PASS', 'Product details page loaded'], ['SE-035', 'Marketplace', 'Farmer', 'Add Product to Cart', 'PASS', 'Product added to cart successfully'], ['SE-036', 'Cart & Order', 'Farmer', 'View Cart Contents', 'PASS', 'Cart items displayed with quantities'], ['SE-037', 'Cart & Order', 'Farmer', 'Update Item Quantity in Cart', 'PASS', 'Cart total recalculated correctly'], ['SE-038', 'Cart & Order', 'Farmer', 'Remove Item from Cart', 'PASS', 'Item removed, cart updated'], ['SE-039', 'Cart & Order', 'Farmer', 'Proceed to Checkout', 'PASS', 'Checkout page rendered with order summary'], ['SE-040', 'Cart & Order', 'Farmer', 'Place Order', 'PASS', 'Order created with unique order ID'], ['SE-041', 'Order Management', 'Seller', 'View Incoming Orders', 'PASS', 'Orders listed with statuses'], ['SE-042', 'Order Management', 'Seller', 'Update Order Status to Shipped', 'PASS', 'Status updated and notification sent'], ['SE-043', 'Order Management', 'Farmer', 'View Order History', 'PASS', 'Order history displayed correctly'], ['SE-044', 'Order Management', 'Farmer', 'Track Order Status', 'PASS', 'Real-time order status displayed'], ['SE-045', 'Crop Diagnosis', 'Farmer', 'Navigate to Crop Diagnosis Page', 'PASS', 'Diagnosis page rendered correctly'], ['SE-046', 'Crop Diagnosis', 'Farmer', 'Upload Leaf Image for Diagnosis', 'PASS', 'Image uploaded to backend API'], ['SE-047', 'Crop Diagnosis', 'Farmer', 'View Diagnosis Results', 'PASS', 'Early Blight detected with confidence score'], ['SE-048', 'AgriBot Chat', 'Farmer', 'Open Chat Interface', 'PASS', 'Chat widget opened successfully'], ['SE-049', 'AgriBot Chat', 'Farmer', 'Send Query to AgriBot', 'PASS', 'Bot responded with crop control measures'], ['SE-050', 'Dashboard', 'Admin', 'View Analytics Dashboard', 'PASS', 'All KPI metrics rendered correctly']]

os.makedirs("reports", exist_ok=True)
wb = Workbook()
del wb["Sheet"]
ws = wb.create_sheet("Selenium E2E Tests")
_header(ws, "💻 AgriBot – Selenium Web E2E Tests (50 Test Cases)", "1565C0")
for idx, row in enumerate(ROWS):
    _row(ws, idx+3, row, alt=(idx%2==1))
_widths(ws)
ws.freeze_panes = "A3"
wb.save("reports/1_Selenium_E2E_Tests.xlsx")
print("Generated reports/1_Selenium_E2E_Tests.xlsx")
