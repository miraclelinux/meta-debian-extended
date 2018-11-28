LICENSE = "GPLv3+"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

require ${COREBASE}/meta/recipes-extended/diffutils/diffutils.inc
inherit debian-package
require recipes-debian/sources/diffutils.inc
FILESPATH_append = ":${COREBASE}/meta/recipes-extended/diffutils/diffutils-3.6"

SRC_URI += " \
           file://0001-Unset-need_charset_alias-when-building-for-musl.patch \
           file://run-ptest \
"
SRC_URI_append_libc-glibc = " file://0001-explicitly-disable-replacing-getopt.patch"

EXTRA_OECONF += "ac_cv_path_PR_PROGRAM=${bindir}/pr --without-libsigsegv-prefix"

# Fix "Argument list too long" error when len(TMPDIR) = 410
acpaths = "-I ./m4"

inherit ptest

RDEPENDS_${PN}-ptest += "make"

do_install_ptest() {
	t=${D}${PTEST_PATH}
	install -D ${S}/build-aux/test-driver $t/build-aux/test-driver
	cp -r ${S}/tests $t/
	install ${B}/tests/Makefile $t/tests/
	sed -e 's,--sysroot=${STAGING_DIR_TARGET},,g' \
	    -e 's|${DEBUG_PREFIX_MAP}||g' \
	    -e 's:${HOSTTOOLS_DIR}/::g' \
	    -e 's:${RECIPE_SYSROOT_NATIVE}::g' \
	    -e 's:${BASE_WORKDIR}/${MULTIMACH_TARGET_SYS}::g' \
	    -e 's|^Makefile:|_Makefile:|' \
	    -e 's|bash|sh|' \
	    -e 's|^top_srcdir = \(.*\)|top_srcdir = ..\/|' \
	    -e 's|^srcdir = \(.*\)|srcdir = .|' \
	    -e 's|"`$(built_programs)`"|diff|' \
	    -e 's|gawk|awk|g' \
	    -i $t/tests/Makefile
}
