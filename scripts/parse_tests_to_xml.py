import os
import glob
import xml.etree.ElementTree as ET

def combine_xml_reports(directory, output_file):
    search_path = os.path.join(directory, "**", "TEST-*.xml")
    xml_files = glob.glob(search_path, recursive=True)
    
    root_suites = ET.Element("testsuites", name="AllTestResults")
    total_tests = 0
    total_failures = 0
    total_errors = 0
    total_time = 0.0
    
    for xml_file in xml_files:
        try:
            tree = ET.parse(xml_file)
            root = tree.getroot()
            
            if root.tag == 'testsuite':
                suites = [root]
            else:
                suites = root.findall('testsuite')
                
            for suite in suites:
                suite_copy = ET.SubElement(root_suites, "testsuite")
                for k, v in suite.attrib.items():
                    suite_copy.set(k, v)
                
                for case in suite.findall('testcase'):
                    case_copy = ET.SubElement(suite_copy, "testcase")
                    for k, v in case.attrib.items():
                        case_copy.set(k, v)
                        
                    for child in case:
                        child_copy = ET.SubElement(case_copy, child.tag)
                        for k, v in child.attrib.items():
                            child_copy.set(k, v)
                        child_copy.text = child.text
                        
                total_tests += int(suite.attrib.get('tests', 0))
                total_failures += int(suite.attrib.get('failures', 0))
                total_errors += int(suite.attrib.get('errors', 0))
                total_time += float(suite.attrib.get('time', 0.0))
        except Exception as e:
            print(f"Error parsing {xml_file}: {e}")
            
    root_suites.set("tests", str(total_tests))
    root_suites.set("failures", str(total_failures))
    root_suites.set("errors", str(total_errors))
    root_suites.set("time", f"{total_time:.3f}")
    
    tree_out = ET.ElementTree(root_suites)
    try:
        ET.indent(tree_out, space="  ", level=0)
    except AttributeError:
        pass
        
    tree_out.write(output_file, encoding="utf-8", xml_declaration=True)
    print(f"Combined {len(xml_files)} files into {output_file}")

if __name__ == "__main__":
    import sys
    search_dir = sys.argv[1] if len(sys.argv) > 1 else "."
    out_file = sys.argv[2] if len(sys.argv) > 2 else "test-results.xml"
    combine_xml_reports(search_dir, out_file)
