# From: poky/meta/recipes-devtools/python/python3-pip
# Rev: 4e0adf684005542430a230692583ed85e659ecd6

SUMMARY = "The PyPA recommended tool for installing Python packages"
HOMEPAGE = "https://pypi.python.org/pypi/pip"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=593c6cd9d639307226978cbcae61ad4b"

DEPENDS += "python3 python3-setuptools-native"

inherit pypi distutils3

inherit debian-package
require recipes-debian/sources/python-pip.inc

DEBIAN_UNPACK_DIR = "${WORKDIR}/pip-${PV}"

RDEPENDS_${PN} = "\
  python3-compile \
  python3-io \
  python3-html \
  python3-json \
  python3-netserver \
  python3-setuptools \
  python3-unixadmin \
  python3-xmlrpc \
"

BBCLASSEXTEND = "native nativesdk"
