# base recipe: meta/recipes-devtools/python/python-setuptools.inc
# base branch: warrior
# base commit: 806de34fda21fb1ab38d6b00566eb3d538d574d0

SUMMARY = "Download, build, install, upgrade, and uninstall Python packages"
HOMEPAGE = "https://pypi.python.org/pypi/setuptools"
SECTION = "devel/python"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;beginline=1;endline=19;md5=9a33897f1bca1160d7aad3835152e158"

PYPI_PACKAGE_EXT = "zip"

inherit pypi

# inherit debian-package should be after inherit pypi
inherit debian-package
require recipes-debian/sources/python-setuptools.inc

DEBIAN_UNPACK_DIR = "${WORKDIR}/python-setuptools-${PV}"

FILESPATH_append = ":${COREBASE}/meta/recipes-devtools/python/files:"

SRC_URI_append_class-native = " file://0001-conditionally-do-not-fetch-code-by-easy_install.patch"

DEPENDS += "${PYTHON_PN}"

RDEPENDS_${PN} = "\
  ${PYTHON_PN}-compile \
  ${PYTHON_PN}-compression \
  ${PYTHON_PN}-ctypes \
  ${PYTHON_PN}-distutils \
  ${PYTHON_PN}-email \
  ${PYTHON_PN}-html \
  ${PYTHON_PN}-netserver \
  ${PYTHON_PN}-numbers \
  ${PYTHON_PN}-pkgutil \
  ${PYTHON_PN}-plistlib \
  ${PYTHON_PN}-shell \
  ${PYTHON_PN}-stringold \
  ${PYTHON_PN}-threading \
  ${PYTHON_PN}-unittest \
  ${PYTHON_PN}-xml \
"
do_install_prepend() {
    install -d ${D}${PYTHON_SITEPACKAGES_DIR}
}

BBCLASSEXTEND = "native nativesdk"
