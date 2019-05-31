# From: poky/meta/recipes-devtools/python/python3-iniparse_0.4.bb
# Rev: 9f8525721672577cbaab96081094211479db4c76
SUMMARY = "Accessing and Modifying INI files"
HOMEPAGE = "https://pypi.python.org/pypi/iniparse/"
LICENSE = "MIT & PSF"
LIC_FILES_CHKSUM = "file://LICENSE-PSF;md5=1c78a5bb3584b353496d5f6f34edb4b2 \
                    file://LICENSE;md5=52f28065af11d69382693b45b5a8eb54"

FILESPATH_append = ":${COREBASE}/meta/recipes-devtools/python/python-iniparse:"
SRC_URI += "file://0001-Add-python-3-compatibility.patch "

inherit pypi distutils3

inherit debian-package
require recipes-debian/sources/python-iniparse.inc

DEBIAN_UNPACK_DIR = "${WORKDIR}/iniparse-${PV}"

RDEPENDS_${PN} += "python3-core python3-six"
DEPENDS += "python3-six"

BBCLASSEXTEND = "native nativesdk"
