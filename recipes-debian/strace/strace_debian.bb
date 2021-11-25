# base recipe: meta/recipes-devtools/strace/strace_4.26.bb
# base branch: warrior
# base commit: c8c383d958807b991e31d22f612ba2a81a3860a4

FILESPATH_append = ":${COREBASE}/meta/recipes-devtools/strace/strace"
SUMMARY = "System call tracing tool"
HOMEPAGE = "http://strace.io"
SECTION = "console/utils"
LICENSE = "LGPL-2.1+ & GPL-2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=5c84d1c6e48e7961ccd2cd2ae32f7bf1"

inherit autotools ptest bluetooth
inherit debian-package
require recipes-debian/sources/strace.inc

SRC_URI += " \
           file://disable-git-version-gen.patch \
           file://more-robust-test-for-m32-mx32-compile-support.patch \
           file://update-gawk-paths.patch \
           file://Makefile-ptest.patch \
           file://run-ptest \
           file://0001-Fix-build-when-using-non-glibc-libc-implementation-o.patch \
           file://0001-caps-abbrev.awk-fix-gawk-s-path.patch \
           file://0001-tests-sigaction-Check-for-mips-and-alpha-before-usin.patch \
           file://ptest-spacesave.patch \
           "
       
PACKAGECONFIG_class-target ??= "\
    ${@bb.utils.contains('DISTRO_FEATURES', 'bluetooth', 'bluez', '', d)} \
"

PACKAGECONFIG[bluez] = "ac_cv_header_bluetooth_bluetooth_h=yes,ac_cv_header_bluetooth_bluetooth_h=no,${BLUEZ}"
PACKAGECONFIG[libunwind] = "--with-libunwind,--without-libunwind,libunwind"

EXTRA_OECONF += "--enable-mpers=no"

CFLAGS_append_libc-musl = " -Dsigcontext_struct=sigcontext"

TESTDIR = "tests"
PTEST_BUILD_HOST_PATTERN = "^(DEB_CHANGELOGTIME|RPM_CHANGELOGTIME|WARN_CFLAGS_FOR_BUILD|LDFLAGS_FOR_BUILD)"

B = "${S}"

do_configure_prepend() {
    autoreconf -i -f -s
}

do_install_append() {
    # We don't ship strace-graph here because it needs perl
    rm ${D}${bindir}/strace-graph
}

do_compile_ptest() {
    oe_runmake -C ${TESTDIR} buildtest-TESTS
}

do_install_ptest() {
    oe_runmake -C ${TESTDIR} install-ptest BUILDDIR=${B} DESTDIR=${D}${PTEST_PATH} TESTDIR=${TESTDIR}
    install -m 755 ${S}/test-driver ${D}${PTEST_PATH}
    install -m 644 ${B}/config.h ${D}${PTEST_PATH}
        sed -i -e '/^src/s/strace.*[1-9]/ptest/' ${D}/${PTEST_PATH}/${TESTDIR}/Makefile
}

RDEPENDS_${PN}-ptest += "make coreutils grep gawk sed"

BBCLASSEXTEND = "native"
TOOLCHAIN = "gcc"
