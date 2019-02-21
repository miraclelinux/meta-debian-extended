require ${COREBASE}/meta/recipes-extended/shadow/shadow.inc

# remove patches from original SRC_URI
SRC_URI_remove_class-target = " \
           file://shadow-update-pam-conf.patch \
           file://shadow-relaxed-usernames.patch \
           "

SRC_URI_remove_class-native = " \
           file://0001-Disable-use-of-syslog-for-sysroot.patch \
           file://allow-for-setting-password-in-clear-text.patch \
           file://commonio.c-fix-unexpected-open-failure-in-chroot-env.patch \
           file://0001-useradd.c-create-parent-directories-when-necessary.patch \
           "
SRC_URI_remove_class-nativesdk = " \
           file://0001-Disable-use-of-syslog-for-sysroot.patch \
           "

inherit debian-package
require recipes-debian/sources/shadow.inc
FILESPATH_append = ":${COREBASE}/meta/recipes-extended/shadow/files"
FILESEXTRAPATHS_prepend := "${THISDIR}/shadow':"

# patch maintenance: https://github.com/iwamatsu/shadow-debian-oe
SRC_URI += " \
           ${@bb.utils.contains('PACKAGECONFIG', 'pam', '${PAM_SRC_URI}', '', d)} \
           file://0001-useradd-copy-extended-attributes-of-home.patch \
           file://0001-update-manpages.patch \
           "

# ignore: shadow-4.1.3-dots-in-usernames.patch
# debian original/adhoc: file://0001-update-manpages.patch

SRC_URI_append_class-target = " \
           file://0001-shadow-Update-pam-conf.patch \
           file://0001-Update-man-man_nopam.patch \
           "

#apply: hadow-update-pam-conf.patch
#	0001-shadow-Update-pam-conf.patch
#ignore: shadow-relaxed-usernames.patch 
#new patch: 0001-Update-man-man_nopam.patch

SRC_URI_append_class-native = " \
           file://0001-Disable-use-of-syslog-for-sysroot_debian.patch \
           file://0002-Allow-for-setting-password-in-clear-text.patch \
           file://0003-Upstream-Status-Inappropriate-OE-specific.patch \
           file://0004-useradd.c-create-parent-directories-when-necessary.patch \
           "

#apply: 0001-Disable-use-of-syslog-for-sysroot.patch 
#       ->0001-Disable-use-of-syslog-for-sysroot_debian.patch
#apply: allow-for-setting-password-in-clear-text.patch 
#	-> 0002-Allow-for-setting-password-in-clear-text.patch
#apply: commonio.c-fix-unexpected-open-failure-in-chroot-env.patch 
#	-> 0003-Upstream-Status-Inappropriate-OE-specific.patch
#apply: 0001-useradd.c-create-parent-directories-when-necessary.patch
#	-> 0004-useradd.c-create-parent-directories-when-necessary.patch 

SRC_URI_append_class-nativesdk = " \
           file://0001-Disable-use-of-syslog-for-sysroot_debian.patch \
           file://0001-Update-man-man_nopam.patch \
           "
#apply: 0001-Disable-use-of-syslog-for-sysroot.patch 
#       ->0001-Disable-use-of-syslog-for-sysroot_debian.patch

# Build falsely assumes that if --enable-libpam is set, we don't need to link against
# libcrypt. This breaks chsh.
BUILD_LDFLAGS_append_class-target = " ${@bb.utils.contains('DISTRO_FEATURES', 'pam', bb.utils.contains('DISTRO_FEATURES', 'libc-crypt',  '-lcrypt', '', d), '', d)}"

BBCLASSEXTEND = "native nativesdk"

DEPENDS += "bison-native"
EXTRA_OECONF += " --disable-man"

# debian package does not provide nologin.
# Remove nologin from ALTERNATIVE_LINK_NAME.
python __anonymous() {
    __package_name = d.getVar('PN')

    __v = d.getVar('ALTERNATIVE_%s' % __package_name)
    __v = __v.replace("nologin", "")
    d.setVar('ALTERNATIVE_%s' % __package_name, __v)

    __v = d.getVar('ALTERNATIVE_%s-doc' % __package_name)
    __v = __v.replace('nologin.8', '')
    d.setVar('ALTERNATIVE_%s-doc' % __package_name, __v)

    d.delVarFlag('ALTERNATIVE_LINK_NAME', 'nologin')
    d.delVarFlag('ALTERNATIVE_LINK_NAME', 'nologin.8')
}
