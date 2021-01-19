# base recipe: meta-python/recipes-devtools/python/python3-pyyaml_5.3.1.bb
# base branch: warrior
# base commit: 6a7ef1fbb8342d31b76128b102e028844b6f3c98
# http://cgit.openembedded.org/meta-openembedded/tree/meta-python/recipes-devtools/python/python3-pyyaml_5.3.1.bb?id=6a7ef1fbb8342d31b76128b102e028844b6f3c98&h=warrior

SUMMARY = "Python support for YAML"
DEPENDS += "libyaml python3-cython-native"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=5591701d32590f9fa94f3bfee820b634"

inherit debian-package
require recipes-debian/sources/pyyaml.inc

DEBIAN_UNPACK_DIR = "${WORKDIR}/PyYAML-${PV}"
S = "${WORKDIR}/PyYAML-${PV}"

inherit setuptools3

RDEPENDS_${PN} += "\
    python3-datetime \
    python3-netclient \
"

BBCLASSEXTEND = "native nativesdk"
