# base recipe: meta/recipes-connectivity/libpcap/libpcap_1.8.1.bb
# base branch: yocto-2.4
# base commit: fd8e632832eb9e0712b2869a3625821aef9cb7e4

SUMMARY = "Interface for user-level network packet capture"
DESCRIPTION = "Libpcap provides a portable framework for low-level network \
monitoring.  Libpcap can provide network statistics collection, \
security monitoring and network debugging."
HOMEPAGE = "http://www.tcpdump.org/"
BUGTRACKER = "http://sourceforge.net/tracker/?group_id=53067&atid=469577"
SECTION = "libs/network"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=5eb289217c160e2920d2e35bddc36453 \
                    file://pcap.h;beginline=1;endline=32;md5=39af3510e011f34b8872f120b1dc31d2"
DEPENDS = "flex-native bison-native"

INC_PR = "r5"

BINCONFIG = "${bindir}/pcap-config"

inherit autotools binconfig-disabled pkgconfig bluetooth debian-package
require recipes-debian/sources/libpcap.inc

EXTRA_OECONF = "--with-pcap=linux"
EXTRA_AUTORECONF += "--exclude=aclocal"

PACKAGECONFIG ??= "${@bb.utils.contains('DISTRO_FEATURES', 'bluetooth', '${BLUEZ}', '', d)} \
                   ${@bb.utils.filter('DISTRO_FEATURES', 'ipv6', d)} \
"
PACKAGECONFIG[bluez4] = "--enable-bluetooth,--disable-bluetooth,bluez4"
# Add a dummy PACKAGECONFIG for bluez5 since it is not supported by libpcap.
PACKAGECONFIG[bluez5] = ",,"
PACKAGECONFIG[dbus] = "--enable-dbus,--disable-dbus,dbus"
PACKAGECONFIG[ipv6] = "--enable-ipv6,--disable-ipv6,"
PACKAGECONFIG[libnl] = "--with-libnl,--without-libnl,libnl"

CPPFLAGS_prepend = "-I${S} "
CFLAGS_prepend = "-I${S} "
CXXFLAGS_prepend = "-I${S} "

do_configure_prepend () {
    sed -i -e's,^V_RPATH_OPT=.*$,V_RPATH_OPT=,' ${S}/pcap-config.in
}

BBCLASSEXTEND = "native"

SRC_URI += " \
    file://libpcap-pkgconfig-support.patch \
    file://0001-Fix-compiler_state_t.ai-usage-when-INET6-is-not-defi.patch \
    file://0002-Add-missing-compiler_state_t-parameter.patch \
    file://fix-grammar-deps.patch \
    file://0010-workaround-for-build-failure.patch \
    file://fix-lds-path.diff \
    file://add-fPIC.diff \
"

#
# make install doesn't cover the shared lib
# make install-shared is just broken (no symlinks)
#

do_configure_prepend () {
    #remove hardcoded references to /usr/include
    sed 's|\([ "^'\''I]\+\)/usr/include/|\1${STAGING_INCDIR}/|g' -i ${S}/configure.ac
}

do_install_prepend () {
    install -d ${D}${libdir}
    install -d ${D}${bindir}
    oe_runmake install-shared DESTDIR=${D}
    oe_libinstall -a -so libpcap ${D}${libdir}
    sed "s|@VERSION@|${PV}|" -i ${B}/libpcap.pc
    install -D -m 0644 libpcap.pc ${D}${libdir}/pkgconfig/libpcap.pc
}
