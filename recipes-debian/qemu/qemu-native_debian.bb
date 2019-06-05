# From poky/meta/recipes-devtools/qemu/qemu-native_3.1.0.bb
# Branch: warrior
# rev: 48522906a261f9a552f13b146aa7f3691be37002
BPN = "qemu"

DEPENDS = "glib-2.0-native zlib-native seabios-native"
RDEPENDS = "seabios-native"

require qemu-native.inc

EXTRA_OECONF_append = " --target-list=${@get_qemu_usermode_target_list(d)} --disable-tools --disable-blobs --disable-guest-agent"

PACKAGECONFIG ??= ""
