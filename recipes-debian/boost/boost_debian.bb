# base recipe: poky/meta/recipes-support/boost/boost_1.67.0.bb
# base branch: warrior
# base commit: c5fc674b4e5a652613346c74eec2e879920ae40a

HOMEPAGE = "http://www.boost.org/"
LICENSE = "BSL-1.0 & MIT & Python-2.0"
LIC_FILES_CHKSUM = "file://LICENSE_1_0.txt;md5=e4224ccaecb14d942c71d31bef20d78c"

inherit debian-package
require recipes-debian/sources/boost1.67.inc
require ${COREBASE}/meta/recipes-support/boost/boost.inc
BOOST_LIBS += " signals"
BOOST_LIBS_append_arm = ""
BJAM_OPTS_append_arm = ""

DEBIAN_UNPACK_DIR = "${WORKDIR}/${BPN}"

FILESPATH_append = ":${COREBASE}/meta/recipes-support/boost/boost"
SRC_URI += "file://arm-intrinsics.patch \
            file://boost-CVE-2012-2677.patch \
            file://boost-math-disable-pch-for-gcc.patch \
            file://0001-Apply-boost-1.62.0-no-forced-flags.patch.patch \
            file://0003-Don-t-set-up-arch-instruction-set-flags-we-do-that-o.patch \
            file://0001-make_x86_64_sysv_elf_gas.S-set-.file-section.patch \
            file://0001-Removed-clang-specific-branch-for-x86-DCAS-based-loa.patch \
           "

addtask slink_task after do_unpack before do_debian_patch
do_slink_task[dirs] = "${DEBIAN_UNPACK_DIR}"
do_slink_task() {
    mkdir -p ${DEBIAN_UNPACK_DIR}/boost
    cd ${DEBIAN_UNPACK_DIR}/boost
    ln -s ../libs/atomic/include/boost/atomic ./
    ln -s ../libs/config/include/boost/config ./
    ln -s ../libs/pool/include/boost/pool ./
    ln -s ../libs/smart_ptr/include/boost/smart_ptr ./
}

