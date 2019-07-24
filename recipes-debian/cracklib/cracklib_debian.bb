# base recipe: meta/recipes-extended/cracklib/cracklib_2.9.5.bb
# base branch: warrior
# base commit: 37f7c03154ab7ad95fdb6fc264bcca2f036789ac

SUMMARY = "Password strength checker library"
HOMEPAGE = "http://sourceforge.net/projects/cracklib"

LICENSE = "LGPLv2.1+"
LIC_FILES_CHKSUM = "file://COPYING.LIB;md5=e3eda01d9815f8d24aae2dbd89b68b06"

inherit debian-package
require recipes-debian/sources/cracklib2.inc

DEPENDS = "cracklib-native zlib"

EXTRA_OECONF = "--without-python --libdir=${base_libdir}"

FILESPATH_append = ":${COREBASE}/meta/recipes-extended/cracklib/cracklib:"
SRC_URI += "file://0001-packlib.c-support-dictionary-byte-order-dependent.patch \
			file://0002-craklib-fix-testnum-and-teststr-failed.patch"

inherit autotools gettext

do_install_append_class-target() {
	create-cracklib-dict -o ${D}${datadir}/cracklib/pw_dict ${D}${datadir}/cracklib/cracklib-small
}

BBCLASSEXTEND = "native nativesdk"
