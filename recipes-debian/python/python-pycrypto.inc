# base recipe: meta-openembedded/meta-python/recipes-devtools/python/python-pycrypto.inc
# base branch: warrior
# base commit: c06be20b249d023849726ee752b76816ccf2e9f4

DESCRIPTION = "Cryptographic modules for Python."
HOMEPAGE = "http://www.pycrypto.org/"
LICENSE = "PSFv2"
LIC_FILES_CHKSUM = "file://COPYRIGHT;md5=35f354d199e8cb7667b059a23578e63d"

FILESEXTRAPATHS_prepend := "${THISDIR}/python-pycrypto:"

DEPENDS += " gmp"

inherit pypi autotools-brokensep

# debian-package must be included after pypi
inherit debian-package
require recipes-debian/sources/python-crypto.inc
DEBIAN_UNPACK_DIR = "${WORKDIR}/pycrypto-${PV}"

SRC_URI += "file://cross-compiling.patch \
           "

do_compile[noexec] = "1"

BBCLASSEXTEND = "native nativesdk"
