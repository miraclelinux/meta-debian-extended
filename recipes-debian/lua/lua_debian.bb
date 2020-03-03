# base recipe: meta-oe/recipes-devtools/lua/lua_5.3.5.bb
# base branch: warrior
# base commit: a24acf94d48d635eca668ea34598c6e5c857e3f8

DESCRIPTION = "Lua is a powerful light-weight programming language designed \
for extending applications."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://doc/readme.html;beginline=318;endline=352;md5=10ffd57d574c60d5b4d6189544e205a9"
HOMEPAGE = "http://www.lua.org/"

inherit debian-package
require recipes-debian/sources/lua5.3.inc

DEPENDS = "readline dpkg-native"
SRC_URI += " \
           file://lua.pc.in \
           "

# if no test suite matches PV release of Lua exactly, download the suite for the closest Lua release.
PV_testsuites = "5.3.4"
inherit pkgconfig binconfig ptest

UCLIBC_PATCHES += "file://uclibc-pthread.patch"
SRC_URI_append_libc-uclibc = "${UCLIBC_PATCHES}"

TARGET_CC_ARCH += " -fPIC ${LDFLAGS}"
EXTRA_OEMAKE = "'CC=${CC} -fPIC' 'CXX=${CXX}' 'MYCFLAGS=${CFLAGS} -DLUA_USE_LINUX -fPIC' MYLDFLAGS='${LDFLAGS}'"

do_debian_patch_append() {
    sed -i -e s:/usr/local:${prefix}:g ${S}/src/luaconf.h
    sed -i -e 's/#include "lua5.3-deb-multiarch.h"/#define DEB_HOST_MULTIARCH "${TARGET_SYS}"/g' ${S}/src/luaconf.h
    sed -i -e "s/export LIBTOOL=libtool --quiet/export LIBTOOL=${TARGET_PREFIX}libtool --quiet/g" ${S}/Makefile
}

do_compile () {
    export PERL5LIB=${STAGING_DIR_NATIVE}/usr/lib/perl-native/perl/5.28.1
    oe_runmake linux
}

do_install () {
    oe_runmake \
        'INSTALL_TOP=${D}${prefix}' \
        'INSTALL_BIN=${D}${bindir}' \
        'INSTALL_INC=${D}${includedir}/' \
        'INSTALL_MAN=${D}${mandir}/man1' \
        'INSTALL_SHARE=${D}${datadir}/lua' \
        'INSTALL_LIB=${D}${libdir}' \
        'INSTALL_CMOD=${D}${libdir}/lua/5.3' \
        install
    install -d ${D}${libdir}/pkgconfig

    sed -e s/@VERSION@/${PV}/ ${WORKDIR}/lua.pc.in > ${WORKDIR}/lua.pc
    install -m 0644 ${WORKDIR}/lua.pc ${D}${libdir}/pkgconfig/
    rmdir ${D}${datadir}/lua/5.3
    rmdir ${D}${datadir}/lua
}

do_install_ptest () {
        cp -R --no-dereference --preserve=mode,links -v ${WORKDIR}/lua-${PV_testsuites}-tests ${D}${PTEST_PATH}/test
}

BBCLASSEXTEND = "native nativesdk"
