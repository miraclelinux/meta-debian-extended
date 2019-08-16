# base recipe: meta/recipes-extended/quota/quota_4.04.bb
# base branch: warrior
# base commit: 811c07db35354bf97e765942f701896a7433f5b7

SUMMARY = "Tools for monitoring & limiting user disk usage per filesystem"
SECTION = "base"
HOMEPAGE = "http://sourceforge.net/projects/linuxquota/"
BUGTRACKER = "http://sourceforge.net/tracker/?group_id=18136&atid=118136"
LICENSE = "BSD & GPLv2+ & LGPLv2.1+"
LIC_FILES_CHKSUM = "file://quota.c;beginline=1;endline=33;md5=331c7d77744bfe0ad24027f0651028ec \
                    file://rquota_server.c;beginline=1;endline=20;md5=fe7e0d7e11c6f820f8fa62a5af71230f \
                    file://svc_socket.c;beginline=1;endline=17;md5=24d5a8792da45910786eeac750be8ceb"

inherit debian-package
require recipes-debian/sources/quota.inc

FILESPATH_append = ":${COREBASE}/meta/recipes-extended/${BPN}/${BPN}:"
SRC_URI += " \
           file://fcntl.patch \
           file://remove_non_posix_types.patch \
          "
SRC_URI_append_libc-musl = " file://replace_getrpcbynumber_r.patch"

CVE_PRODUCT = "linux_diskquota"

DEPENDS = "gettext-native e2fsprogs libnl dbus"

inherit autotools-brokensep gettext pkgconfig

CFLAGS += "${@bb.utils.contains('PACKAGECONFIG', 'rpc', '-I${STAGING_INCDIR}/tirpc', '', d)}"
LDFLAGS += "${@bb.utils.contains('PACKAGECONFIG', 'rpc', '-ltirpc', '', d)}"
ASNEEDED = ""

PACKAGECONFIG ??= "tcp-wrappers rpc bsd"
PACKAGECONFIG_libc-musl = "tcp-wrappers rpc"

PACKAGECONFIG[tcp-wrappers] = "--enable-libwrap,--disable-libwrap,tcp-wrappers"
PACKAGECONFIG[rpc] = "--enable-rpc,--disable-rpc,libtirpc"
PACKAGECONFIG[bsd] = "--enable-bsd_behaviour=yes,--enable-bsd_behaviour=no,"
PACKAGECONFIG[ldapmail] = "--enable-ldapmail,--disable-ldapmail,openldap"
