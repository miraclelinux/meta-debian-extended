SUMMARY = "Stream EDitor (text filtering utility)"
HOMEPAGE = "http://www.gnu.org/software/sed/"
LICENSE = "GPLv3+"
LIC_FILES_CHKSUM = "file://COPYING;md5=c678957b0c8e964aa6c70fd77641a71e \
                    file://sed/sed.h;beginline=1;endline=17;md5=e5404d899f628f18544e07fab63d2854"
SECTION = "console/utils"

inherit debian-package
require recipes-debian/sources/sed.inc
FILESPATH_append = ":${COREBASE}/meta/recipes-extended/sed/sed-4.2.2"
# source format is 3.0 (quilt) but there is no debian/patches
DEBIAN_QUILT_PATCHES = ""


SRC_URI += " \
	file://0001-install-ptest.patch \
	file://0001-Unset-need_charset_alias-when-building-for-musl.patch \
"

#SRC_URI += " \
#           file://sed-add-ptest.patch \
#	   file://0001-Unset-need_charset_alias-when-building-for-musl.patch \
#           file://run-ptest \
#"

inherit autotools texinfo update-alternatives gettext ptest
RDEPENDS_${PN}-ptest += "make ${PN}"
RRECOMMENDS_${PN}-ptest_append_libc-glibc = " locale-base-ru-ru"

EXTRA_OECONF = "--disable-acl \
                ${@bb.utils.contains('PTEST_ENABLED', '1', '--enable-regex-tests', '', d)}"

do_install () {
	autotools_do_install
	install -d ${D}${base_bindir}
	if [ ! ${D}${bindir} -ef ${D}${base_bindir} ]; then
	    mv ${D}${bindir}/sed ${D}${base_bindir}/sed
	    rmdir ${D}${bindir}/
	fi
}

ALTERNATIVE_${PN} = "sed"
ALTERNATIVE_LINK_NAME[sed] = "${base_bindir}/sed"
ALTERNATIVE_PRIORITY = "100"

TESTDIR = "testsuite"

do_compile_ptest() {
	oe_runmake -C ${TESTDIR} buildtest-TESTS
}

do_install_ptest() {
	oe_runmake -C ${TESTDIR} install-ptest BUILDDIR=${B} DESTDIR=${D}${PTEST_PATH} TESTDIR=${TESTDIR}
	sed -e 's,--sysroot=${STAGING_DIR_TARGET},,g' \
	    -e 's|${DEBUG_PREFIX_MAP}||g' \
	    -e 's:${HOSTTOOLS_DIR}/::g' \
	    -e 's:${RECIPE_SYSROOT_NATIVE}::g' \
	    -e 's:${BASE_WORKDIR}/${MULTIMACH_TARGET_SYS}::g' \
	    -i ${D}${PTEST_PATH}/${TESTDIR}/Makefile
}

RPROVIDES_${PN} += "${@bb.utils.contains('DISTRO_FEATURES', 'usrmerge', '/bin/sed', '', d)}"
