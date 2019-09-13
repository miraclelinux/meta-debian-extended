# base recipe: meta/recipes-core/glibc/cross-localedef-native_2.29.bb
# base branch: warrior
# base commit: 3c6764b09888e2025536b8bc73ce548cf53a1579

SUMMARY = "Cross locale generation tool for glibc"
HOMEPAGE = "http://www.gnu.org/software/libc/libc.html"
SECTION = "libs"
LICENSE = "LGPL-2.1"

LIC_FILES_CHKSUM = "file://LICENSES;md5=cfc0ed77a9f62fa62eded042ebe31d72 \
      file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
      file://posix/rxspencer/COPYRIGHT;md5=dc5485bb394a13b2332ec1c785f5d83a \
      file://COPYING.LIB;md5=4fbd65380cdd255951079008b364516c"

# Tell autotools that we're working in the localedef directory
#
AUTOTOOLS_SCRIPT_PATH = "${S}/localedef"

inherit debian-package
require recipes-debian/sources/glibc.inc
FILESPATH_append = ":${COREBASE}/meta/recipes-core/glibc/glibc"
DEBIAN_UNPACK_DIR = "${WORKDIR}/glibc-${PV}"

inherit native
inherit autotools

FILESEXTRAPATHS =. "${FILE_DIRNAME}/${PN}:${FILE_DIRNAME}/glibc:"

SRCREV_localedef ?= "cd9f958c4c94a638fa7b2b4e21627364f1a1a655"

SRC_URI += " \
           git://github.com/kraj/localedef;branch=master;name=localedef;destsuffix=glibc-${PV}/localedef \
           file://0016-timezone-re-written-tzselect-as-posix-sh.patch \
           file://0017-Remove-bash-dependency-for-nscd-init-script.patch \
           file://0018-eglibc-Cross-building-and-testing-instructions.patch \
           file://0022-eglibc-Forward-port-cross-locale-generation-support.patch \
           file://0023-Define-DUMMY_LOCALE_T-if-not-defined.patch \
           file://0024-localedef-add-to-archive-uses-a-hard-coded-locale-pa.patch \
"
# Ignore patches 0019, 0020 and 0021 because Debian already has similar patches.
#           file://0019-eglibc-Help-bootstrap-cross-toolchain.patch \
#           file://0020-eglibc-Clear-cache-lines-on-ppc8xx.patch \
#           file://0021-eglibc-Resolve-__fpscr_values-on-SH4.patch \
#

# Makes for a rather long rev (22 characters), but...
#
SRCREV_FORMAT = "glibc_localedef"

EXTRA_OECONF = "--with-glibc=${S}"
CFLAGS += "-fgnu89-inline -std=gnu99 -DIS_IN\(x\)='0'"

do_install() {
	install -d ${D}${bindir}
	install -m 0755 ${B}/localedef ${D}${bindir}/cross-localedef
}
