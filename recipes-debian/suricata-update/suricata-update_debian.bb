# base recipe: meta-security/recipes-ids/suricata/python3-suricata-update_1.1.1.bb
# base branch: master
# base commit: 6053e8b8e2f868ad67eeb0764b437b39e531da93
# http://git.yoctoproject.org/cgit/cgit.cgi/meta-security/tree/recipes-ids/suricata/python3-suricata-update_1.1.1.bb?id=6053e8b8e2f868ad67eeb0764b437b39e531da93

SUMMARY = "The tool for updating your Suricata rules. "
HOMEPAGE = "http://suricata-ids.org/"
SECTION = "security Monitor/Admin"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://LICENSE;beginline=1;endline=2;md5=c70d8d3310941dcdfcd1e02800a1f548"

inherit debian-package
require recipes-debian/sources/suricata-update.inc

inherit python3native setuptools3

RDEPENDS_${PN} = "python3-pyyaml python3-logging"
