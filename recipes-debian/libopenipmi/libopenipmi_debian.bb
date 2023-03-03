#base recipe: meta-openembedded/meta-networking/recipes-support/openipmi/openipmi_2.0.25.bb
#base branch: warrior
#base commit: a24acf94d48d635eca668ea34598c6e5c857e3f8
SUMMARY = "IPMI (Intelligent Platform Management Interface) library"
DESCRIPTION = "OpenIPMI is an effort to create a full-function IPMI system, \
to allow full access to all IPMI information on a server \
and to abstract it to a level that will make it easy to use"

HOMEPAGE = "http://openipmi.sourceforge.net"

DEPENDS = " \
    glib-2.0 \
    ncurses \
    net-snmp \
    openssl \
    popt \
    python \
    swig-native \
    "

LICENSE = "GPLv2 & LGPLv2.1 & BSD"

LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f \
                    file://COPYING.LIB;md5=d8045f3b8f929c1cb29a1e3fd737b499 \
                    file://COPYING.BSD;md5=4b318d4160eb69c8ee53452feb1b4cdf \
                    "

inherit debian-package
require recipes-debian/sources/openipmi.inc

SRC_URI += "file://fix-symlink-install-error-in-cmdlang.patch \
           file://openipmi-no-openipmigui-man.patch \
           file://openipmi-remove-host-path-from-la_LDFLAGS.patch \
           file://ipmi-init-fix-the-arguments.patch \
           file://do-not-install-pyc-and-pyo.patch \
           file://include_sys_types.patch \
           file://openipmigui-not-compile-pyc-pyo.patch \
           "

DEBIAN_UNPACK_DIR = "${WORKDIR}/OpenIPMI-${PV}"

inherit autotools-brokensep pkgconfig pythonnative perlnative cpan-base

EXTRA_OECONF = "--with-perl='${STAGING_BINDIR_NATIVE}/perl-native/perl' \
                --with-python='${STAGING_BINDIR_NATIVE}/python-native/python' \
                --with-pythoninstall='${PYTHON_SITEPACKAGES_DIR}' \
                --with-glibver=2.0"

DISABLE_STATIC = ""

PACKAGECONFIG ??= "gdbm"
PACKAGECONFIG[gdbm] = "ac_cv_header_gdbm_h=yes,ac_cv_header_gdbm_h=no,gdbm,"

FILES_${PN}-dbg += " \
    ${libdir}/perl/vendor_perl/*/auto/OpenIPMI/.debug \
    ${PYTHON_SITEPACKAGES_DIR}/.debug \
    "

do_configure () {

    # Let's perform regular configuration first then handle perl issues.
    autotools_do_configure

    perl_ver=`perl -V:version | cut -d\' -f 2`
    
    # Force openipmi perl bindings to be compiled using perl-native instead of
    # the host's perl. Set the proper install directory for the resulting
    # openipmi.pm and openipmi.so
    for i in ${S}/swig/Makefile ${S}/swig/perl/Makefile; do
        echo "SAL: i = $i"
        echo "SAL: STAGING_INCDIR_NATIVE = $STAGING_INCDIR_NATIVE"
        echo "SAL: libdir = $libdir"
        sed -i -e "/^PERL_CFLAGS/s:-I/usr/local/include:-I${STAGING_INCDIR_NATIVE}:g" $i
        sed -i -e "/^PERL_CFLAGS/s:-I .* :-I ${STAGING_LIBDIR}${PERL_OWN_DIR}/perl5/${@get_perl_version(d)}/${@get_perl_arch(d)}/CORE :g" $i
        sed -i -e "/^PERL_INSTALL_DIR/s:^PERL_INSTALL_DIR = .*:PERL_INSTALL_DIR = ${libdir}/perl/vendor_perl/$perl_ver:g" $i
    done
}

do_install () {
    install -d ${D}${libdir}
    cp -r --no-preserve=ownership ${S}/*/.libs/*${SOLIBS} ${D}${libdir}
    cp -r --no-preserve=ownership ${S}/*/.libs/*.a ${D}${libdir}
    rm -rf  ${D}${libdir}/libOpenIPMIglib*

    ln -sf libIPMIlanserv.so.0.0.1 ${D}${libdir}/libIPMIlanserv.so
    ln -sf libOpenIPMI.so.0.0.5 ${D}${libdir}/libOpenIPMI.so
    ln -sf libOpenIPMIcmdlang.so.0.0.5 ${D}${libdir}/libOpenIPMIcmdlang.so
    ln -sf libOpenIPMIposix.so.0.0.1 ${D}${libdir}/libOpenIPMIposix.so
    ln -sf libOpenIPMIpthread.so.0.0.1 ${D}${libdir}/libOpenIPMIpthread.so
    ln -sf libOpenIPMIui.so.1.0.1 ${D}${libdir}/libOpenIPMIui.so
    ln -sf libOpenIPMIutils.so.0.0.1 ${D}${libdir}/libOpenIPMIutils.so

    install -d ${D}${includedir}/OpenIPMI/internal
    cp -r --no-preserve=ownership ${S}/include/OpenIPMI/*.h ${D}${includedir}/OpenIPMI
    cp -r --no-preserve=ownership ${S}/include/OpenIPMI/internal/*.h ${D}${includedir}/OpenIPMI/internal
    cp -r --no-preserve=ownership ${S}/lanserv/OpenIPMI//*.h ${D}${includedir}/OpenIPMI

    install -d ${D}${libdir}/pkgconfig
    install -m 644 ${S}/OpenIPMI.pc ${D}${libdir}/pkgconfig
    install -m 644 ${S}/OpenIPMIcmdlang.pc ${D}${libdir}/pkgconfig
    install -m 644 ${S}/OpenIPMIposix.pc ${D}${libdir}/pkgconfig
    install -m 644 ${S}/OpenIPMIpthread.pc ${D}${libdir}/pkgconfig
    install -m 644 ${S}/OpenIPMIui.pc ${D}${libdir}/pkgconfig
    install -m 644 ${S}/OpenIPMIutils.pc ${D}${libdir}/pkgconfig
}
